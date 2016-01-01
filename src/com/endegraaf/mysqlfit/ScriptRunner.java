package com.endegraaf.mysqlfit;

/*
 * Slightly modified version of the com.ibatis.common.jdbc.ScriptRunner class
 * from the iBATIS Apache project. Only removed dependency on Resource class
 * and a constructor 
 * GPSHansl, 06.08.2015: regex for delimiter, rearrange comment/delimiter detection, remove some ide warnings.
 */
/*
 *  Copyright 2004 Clinton Begin
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mysql.jdbc.PreparedStatement;

/**
 * Tool to run database scripts
 */
public class ScriptRunner {

    private static final String DEFAULT_DELIMITER = ";";
    /**
     * regex to detect delimiter.
     * ignores spaces, allows delimiter in comment, allows an equals-sign
     */
    public static final Pattern delimP = Pattern.compile("^\\s*(--)?\\s*delimiter\\s*=?\\s*([^\\s]+)+\\s*.*$", Pattern.CASE_INSENSITIVE);

    private final Connection connection;

    private final boolean stopOnError;
    private final boolean autoCommit;
    
    @SuppressWarnings("UseOfSystemOutOrSystemErr")
    private PrintWriter logWriter = new PrintWriter(System.out);
    @SuppressWarnings("UseOfSystemOutOrSystemErr")
    private PrintWriter errorLogWriter = new PrintWriter(System.err);

    private String delimiter = DEFAULT_DELIMITER;
    private boolean fullLineDelimiter = false;
	private String[] returnArray;
	List<List<String>> returnValueList = new ArrayList();
    

    /**
     * Default constructor
     */
    public ScriptRunner(Connection connection, boolean autoCommit,
            boolean stopOnError) {
        this.connection = connection;
        this.autoCommit = autoCommit;
        this.stopOnError = stopOnError;
    }

    public void setDelimiter(String delimiter, boolean fullLineDelimiter) {
        this.delimiter = delimiter;
        this.fullLineDelimiter = fullLineDelimiter;
    }

    /**
     * Setter for logWriter property
     *
     * @param logWriter - the new value of the logWriter property
     */
    public void setLogWriter(PrintWriter logWriter) {
        this.logWriter = logWriter;
    }

    /**
     * Setter for errorLogWriter property
     *
     * @param errorLogWriter - the new value of the errorLogWriter property
     */
    public void setErrorLogWriter(PrintWriter errorLogWriter) {
        this.errorLogWriter = errorLogWriter;
    }

    /**
     * Runs an SQL script (read in using the Reader parameter)
     *
     * @param reader - the source of the script
     */
    public String[] runScript(Reader reader) throws IOException, SQLException {
        try {
            boolean originalAutoCommit = connection.getAutoCommit();
            try {
                if (originalAutoCommit != this.autoCommit) {
                    connection.setAutoCommit(this.autoCommit);
                }
                System.out.println("");
                System.out.println("runScript function in ScriptRunner.java");
                System.out.println(reader);
                returnArray = runScript(connection, reader);
            } finally {
                connection.setAutoCommit(originalAutoCommit);
            }
        } catch (IOException | SQLException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error running script.  Cause: " + e, e);
        }
		return returnArray;
    }

    /**
     * Runs an SQL script (read in using the Reader parameter) using the
     * connection passed in
     *
     * @param conn - the connection to use for the script
     * @param reader - the source of the script
     * @throws SQLException if any SQL errors occur
     * @throws IOException if there is an error reading from the Reader
     */
    protected String[] runScript(Connection conn, Reader reader) throws IOException,
            SQLException {
        StringBuffer command = null;
        
        try {
            LineNumberReader lineReader = new LineNumberReader(reader);
            String line;

            while ((line = lineReader.readLine()) != null) {
                if (command == null) {
                    command = new StringBuffer();
                }
                String trimmedLine = line.trim();
                
                //System.out.println("Line: " + trimmedLine );
                
                final Matcher delimMatch = delimP.matcher(trimmedLine);
                if (trimmedLine.length() < 1
                        || trimmedLine.startsWith("//")) {
                    // Do nothing
                } else if (delimMatch.matches()) {
                    setDelimiter(delimMatch.group(2), false);
                } else if (trimmedLine.startsWith("--")) {
                    println(trimmedLine);
                } else if (trimmedLine.length() < 1
                        || trimmedLine.startsWith("--")) {
                    // Do nothing
                } else if (!fullLineDelimiter
                        && trimmedLine.endsWith(getDelimiter())
                        || fullLineDelimiter
                        && trimmedLine.equals(getDelimiter())) {
                    command.append(line.substring(0, line
                            .lastIndexOf(getDelimiter())));
                    command.append(" ");
                    Statement statement = conn.createStatement();
                    System.out.println("Level 2 - Try to execute the command over the connection.");
                    //System.out.println(command);

                    boolean hasResults = false;
                    try {
                        hasResults = statement.execute(command.toString());
//                        System.out.println("Value of has results [" + hasResults + "]");
                    } catch (SQLException e) {
                        final String errText = String.format("Error executing '%s' (line %d): %s", command, lineReader.getLineNumber(), e.getMessage());
                        if (stopOnError) {
                            throw new SQLException(errText, e);
                        } else {
                            println(errText);
                        }
                    }

                    if (autoCommit && !conn.getAutoCommit()) {
                        conn.commit();
                    }

                    ResultSet rs = statement.getResultSet();
                    if (hasResults && rs != null) {
                    	
//                    	System.out.println("Has results and has result set!");
                    	
                        ResultSetMetaData md = rs.getMetaData();
                        int cols = md.getColumnCount();
                        
//                        System.out.print("I Found " + cols + " columns ");
                        
                        String[] attributeName = new String[cols];
                        String[] attributeValue= new String[cols];
                        
                        for (int i = 0; i < cols; i++) { // starts counting at 1
                        	attributeName[i]=md.getColumnLabel(i+1);
                        }
                        System.out.println("");
                        int teller=0;
                        while (rs.next()) {
                        	teller++;
                            for (int i = 0; i < cols; i++) {
                            	attributeValue[i]=rs.getString(i+1);
                            	//String value = rs.getString(i);
                            	//System.out.print(value + "\t");
                            }
//                            System.out.println("Waarde van teller [" + teller + "]");
                        }
                        /* two arrays, one for the attributeNames, one for the Values.
                           required output:
                           'attributeName','attributeValue'
                          */
                        for (int i=0; i<teller;i++){
                        	returnArray = returnValue(attributeName[i],attributeValue[i]) ;
                        }
                    }
                    
                    command = null;
                    try {
                        statement.close();
                    } catch (Exception e) {
                        // Ignore to workaround a bug in Jakarta DBCP
                    }
                } else {
                    command.append(line);
                    command.append("\n");
                }
            }
            if (!autoCommit) {
                conn.commit();
            }
            
            return returnArray;
            
        } catch (Exception e) {
            throw new IOException(String.format("Error executing '%s': %s", command, e.getMessage()), e);
        } finally {
            conn.rollback();
            flush();
        }
    }
	   
    public String[] returnValue(String attributeName, String attributeValue){
    	
    	
    	
		String val[] = {attributeName,attributeValue};
    	return val;
    }
//    
    
    private String getDelimiter() {
        return delimiter;
    }

    @SuppressWarnings("UseOfSystemOutOrSystemErr")
    private void print(Object o) {
        if (logWriter != null) {
            System.out.print(o);
        }
    }

    private void println(Object o) {
        if (logWriter != null) {
            logWriter.println(o);
        }
    }

    private void printlnError(Object o) {
        if (errorLogWriter != null) {
            errorLogWriter.println(o);
        }
    }

    private void flush() {
        if (logWriter != null) {
            logWriter.flush();
        }
        if (errorLogWriter != null) {
            errorLogWriter.flush();
        }
    }
}

