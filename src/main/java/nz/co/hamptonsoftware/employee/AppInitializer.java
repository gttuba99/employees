package nz.co.hamptonsoftware.employee;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import nz.co.hamptonsoftware.employee.domain.Employee;

public class AppInitializer implements WebApplicationInitializer {

    private static final String CONFIG_LOCATION = "nz.co.hamptonsoftware.employee.config";
    private static final String MAPPING_URL = "/*";

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        WebApplicationContext context = getContext();
        servletContext.addListener(new ContextLoaderListener(context));
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("DispatcherServlet", new DispatcherServlet(context));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping(MAPPING_URL);
     }

    private AnnotationConfigWebApplicationContext getContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation(CONFIG_LOCATION);
        return context;
    }
    
    private void loadDataFromFile() throws IOException, ParseException {
    	Reader reader = Files.newBufferedReader(Paths.get("jdbc/test-data.csv"));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
        for (CSVRecord csvRecord : csvParser) {
        	Employee emp = new Employee(new Integer(0),csvRecord.get("firstName"),csvRecord.get("middleInitial"), csvRecord.get("lastName"),
        			new SimpleDateFormat("yyyy-MM-dd").parse(csvRecord.get("dateOfBirth")),new SimpleDateFormat("yyyy-MM-dd").parse(csvRecord.get("dateOfEmployment")),new Integer(1));
        }
        csvParser.close();
    }


}
