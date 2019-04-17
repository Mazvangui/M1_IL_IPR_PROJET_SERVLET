
 
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;
 
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
 
@WebServlet("/")
public class influxdbServlet extends HttpServlet {
    @Inject
    InfluxDB influxDB;;
 
    private static final long serialVersionUID = 1L;
 
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	InfluxDB influxDB = InfluxDBFactory.connect("http://localhost:8086");
    	String dbName = "AbdelDB";
        PrintWriter out = response.getWriter();
        /* Create a 'batch' of example 'points'
        BatchPoints batchPoints = BatchPoints.database(dbname).tag("async", "true").retentionPolicy("autogen")
                .consistency(InfluxDB.ConsistencyLevel.ALL).tag("BatchTag", "BatchTagValue") 
                .build();
 
        long free = Runtime.getRuntime().freeMemory();
        Point point1 = Point.measurement("memory").time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .addField("free", free).tag("MemoryTag", "MemoryTagValue")                                               
                .build();
        batchPoints.point(point1);
 
        // Write them to InfluxDB
        influxDB.write(batchPoints);
         */
        Query query = new Query("SELECT value FROM temperature LIMIT 10", dbName);
        QueryResult queryResult = influxDB.query(query);
 
        // iterate the results and print details
        for (QueryResult.Result result : queryResult.getResults()) {
 
            // print details of the entire result
            System.out.println(result.toString());
 
            // iterate the series within the result
            for (QueryResult.Series series : result.getSeries()) {
                out.println("table = " + series.getName());
                //out.println("series.getColumns() = " + series.getColumns());
                out.println("10 valeur = " + series.getValues());
                //out.println("series.getTags() = " + series.getTags());
            }
        }
        query = new Query("SELECT value FROM radiation LIMIT 10", dbName);
        queryResult = influxDB.query(query);
 
        // iterate the results and print details
        for (QueryResult.Result result : queryResult.getResults()) {
 
            // print details of the entire result
            System.out.println(result.toString());
 
            // iterate the series within the result
            for (QueryResult.Series series : result.getSeries()) {
                out.println("table = " + series.getName());
                //out.println("series.getColumns() = " + series.getColumns());
                out.println("10 valeur = " + series.getValues());
                //out.println("series.getTags() = " + series.getTags());
            }
        }
        out.flush();
 
    }
 
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }
 
}