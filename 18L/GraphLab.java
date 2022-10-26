//+++++++++++++++++++++++ GraphLab ++++++++++++++++++++++++++++++++++
import javax.swing.*;
import java.awt.*;
import java.awt.BorderLayout;
import java.util.*;
import java.io.File;
/**
 * GraphLab
 * Reads in a graph file specified as the only argument.
 * Creates a graph from the file and displays it.
 * 
 * @author rdb
 * Created 11/1/10
 * derived from code written by Jonathan Brown
 * 
 * 03/30/14 rdb: Created batch mode to be used by TestDigraph
 * 05/17/14 rdb: added batch command processing; gives finer control
 *               over execution of toString/longString of Node and Digraph
 *               changed query language to use 
 *               g and G for Graph.toString and Graph.longString
 *               n and N for Node.toString and Node.longString
 */

public class GraphLab 
{
    //----------------------- class variables --------------------------
    static boolean interactive = true;
    static boolean longOutput = true;
    
    //----------------------- instance variables -----------------------
    private Digraph       graph = null;
    private String        graphFileName = null;
    private int           lineNum = 1;    // input line number
    
    //++++++++++++++++++++++++++++ Constructors ++++++++++++++++++++++++
    /**
     * Reads in the graph file.
     * @param title String
     * @param filename String
     */
    public GraphLab( String title, String filename )     
    {
        graph = readGraphFile( filename );
        graphFileName = filename;
        printGraph( graph );
        if ( interactive )
            query();
    }
    /**
     * Batch motivated constructor. No initial file read. Used by TestDigraph.
     */
    public GraphLab()
    {
        interactive = false;
    }
    /**
     * Batch command processor.
     * @param cmdFile String     file of commands
     */
    public GraphLab( String cmdFile )
    {
        interactive = false;
        batch( cmdFile );
    }
    
    //++++++++++++++++++++++++++ Private Methods +++++++++++++++++++++++
    //------------------------ parseLine -------------------------------
    /**
     * parse a line of input from the graph file. Lines are of the form:
     *    edge node1 node2
     * 
     * where node1 and node2 are arbitrary Strings (with no spaces) that 
     *       represent a node in the graph. If either node id has not yet been
     *       encountered create a Node object for it and add it to the Digraph
     *       object.
     * Create an Edge from node1 to node2 and add it to the list of edges
     *    in the node1 object.
     * Print an error message for lines that don't start with "edge" or that
     *    don't have 2 node fields; you can ignore extra characters after the
     *    first 3 tokens on the line.
     * @param line String
     */
    private void parseLine( String line )
    {
        if ( line == null )
            return;
        System.out.println( lineNum + ": " + line ); 
        String trimLine = line.trim();
        
        if ( trimLine.length() == 0 || trimLine.startsWith( "#" ) )
            return;
        // We have a "real" input line when reach here
        String[] tokens = line.split( " +" );
        String cmd = tokens[ 0 ];
        if ( cmd.equals( "edge" ) )
        {
            if ( tokens.length < 3 )
                System.err.println( "Error: need 2 args for Edge: " + line );
            else       
                graph.addEdge( tokens[ 1 ], tokens[ 2 ] );
        }
        else if ( cmd.equals( "node" ) )
        {
            for ( int n = 1; n < tokens.length; n++ )
                graph.addNode( tokens[ n ] );
        }
        else 
            System.err.println( "Error: Not a valid command: " + line );      
    }
    
    //-------------------------- query() --------------------------------
    /**
     * Prompt user for queries to the graph.
     */
    private void query()
    {
        String prompt = "Enter command: \n"
            +  "             f       read new graph file\n"
            +  "             g       graph.toString)\n"
            +  "             G       graph.longString\n"
            +  "             n id    node.toString\n"
            +  "             N id    node.longString";
        String msg = "";
        
        String cmd = JOptionPane.showInputDialog( null, prompt );
        while ( cmd != null && cmd.trim().length() > 0 )
        {
            msg  = executeCommand( cmd );
            cmd = JOptionPane.showInputDialog( null, msg + "\n\n" + prompt );
        }
    }
    //-------------------- batch( String ) ------------------------------
    /**
     * Read commands from a command file.
     * @param filename String
     */
    void batch( String filename )
    {
        try
        {
            Scanner cmdReader = new Scanner( new File( filename ) );
            while ( cmdReader.hasNextLine() )
            {
                String msg = null;
                String cmd = cmdReader.nextLine();
                cmd = cmd.trim();
                if ( cmd.length() > 0 )
                {
                    try 
                    {
                        System.out.println(  "<==Command: " + cmd );
                        if ( cmd.charAt( 0 ) != '#' )
                        {
                            msg = executeCommand( cmd );
                            System.out.println( "===>" +  msg );
                        }
                    }
                    catch ( Exception ex )
                    {
                        System.out.println( "****** " + ex.getClass().getName()
                                               + " " + ex.getMessage() );
                    }
                }
            }
        }
        catch ( java.io.FileNotFoundException e )
        {
            System.err.println( filename + ":" + e );
            System.exit( 1 );
        }
    }
    //--------------------- executeCommand( String ) ------------------
    /**
     * Execute a command.
     * @param cmd String
     * @return String        message describing results of command exec.
     */
    private String executeCommand( String cmd )
    {
        String msg = "";
        cmd = cmd.trim();
        char op = cmd.charAt( 0 );
        String[] words = cmd.split( " " );
        switch ( op )
        {
            case 'f': 
                if ( words.length > 1 )
                    graph = readGraphFile( words[ 1 ] );
                else 
                    graph = readGraphFile();
                msg = "++";
                break;
            case 'g': 
                msg = graph.toString();
                break;
            case 'G': 
                msg = graph.longString();
                break;
            case 'n': 
            case 'N':
                String id = cmd.substring( 1 ).trim();
                if ( id.length() > 0 )
                {
                    Node n = graph.getNode( id );
                    if ( n == null )
                        msg = "Unknown id " + id;
                    else if ( op == 'n' )
                        msg = graph.getNode( id ).toString();
                    else
                        msg = graph.getNode( id ).longString();
                }
                else 
                    msg = "No id specified";
                break;
            default:
                msg = "Invalid query: " + cmd;
        }
        return msg;
    }   
    //------------------ readGraphFile() --------------------
    /**
     * Open a graph input file using a JFileChooser to get the file name.
     * @return Digraph
     */
    private Digraph readGraphFile()
    {
        graphFileName = Utilities.getFileName( "open a graph file" );
        if ( graphFileName == null || graphFileName.length() == 0 )
            return new Digraph();  // return empty graph
        else         
            return readGraphFile( graphFileName );
    }
    //------------------ readGraphFile( String filename ) ---------------
    /**
     * Opens a new graph definition file file and resets all variables.
     * The command processor actually parses the file; see that for
     * more details.
     * @param filename String
     * @return Digraph
     */
    Digraph readGraphFile( String filename )
    {
        graphFileName = filename;
        Scanner reader;
        graph = new Digraph();
        lineNum = 1;
        
        try
        {
            reader = new Scanner( new File( filename ) );
            while ( reader.hasNextLine() )
            {
                parseLine( reader.nextLine() );
                lineNum++;
            }
        }
        catch ( java.io.FileNotFoundException e )
        {
            System.err.println( e );
        }
        return graph;
    }
    //------------------ printGraph( Digraph ) ---------------
    /**
     * Print the graph to System.out.
     * @param graf Digraph
     * @return Digraph
     */
    Digraph printGraph( Digraph graf )
    {
        System.out.println( "------ " + graphFileName + " ----------" );
        if ( longOutput )
            System.out.println( graf.longString() );
        else
            System.out.println( graf.toString() );                        
        return graf;
    }
    //+++++++++++++++++++++++ main +++++++++++++++++++++++++++++++++++++
    /**
     * Application main.
     * @param args String[]   command line arguments
     */
    public static void main( String [ ] args ) 
    {
        if ( args.length > 0 && args[ 0 ].equals( "-c" ) )
        {
            if ( args.length > 1 ) // invoke batch command processing
                new GraphLab( args[ 1 ] );
            else
            {
                System.err.println( "ERROR. -c needs a command file name" );
                System.exit( -1 );
            }
        }
        else if ( args.length > 0 )
        {
            if ( args[ 0 ].equals( "-" ) )
            {
                GraphLab.interactive = false;
                new GraphLab( "GraphLab", args[ 1 ] );
            }
            else
                new GraphLab( "GraphLab", args[ 0 ] );
        }
        else
            new GraphLab( "GraphLab", "graph1.txt" );
    }
}
