package com.isu.csvdatasource;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.google.visualization.datasource.DataSourceServlet;
import com.google.visualization.datasource.base.TypeMismatchException;
import com.google.visualization.datasource.datatable.ColumnDescription;
import com.google.visualization.datasource.datatable.DataTable;
import com.google.visualization.datasource.datatable.value.ValueType;
import com.google.visualization.datasource.query.Query;

public class SimpleExampleServlet extends DataSourceServlet {
	public DataTable generateDataTable(Query query, HttpServletRequest request) {
		System.out.println("the request is here "+query+ " and the request is :-"+request);
	    // Create a data table,
	    DataTable data = new DataTable();
	    
		ArrayList cd = new ArrayList();
	    cd.add(new ColumnDescription("name", ValueType.TEXT, "Animal name"));
	    cd.add(new ColumnDescription("link", ValueType.TEXT, "Link to wikipedia"));
	    cd.add(new ColumnDescription("population", ValueType.NUMBER, "Population size"));
	    cd.add(new ColumnDescription("vegeterian", ValueType.BOOLEAN, "Vegetarian?"));

	    data.addColumns(cd);

	    // Fill the data table.
	    try {
	      data.addRowFromValues("Aye-aye", "http://en.wikipedia.org/wiki/Aye-aye", 100, true);
	      data.addRowFromValues("Sloth", "http://en.wikipedia.org/wiki/Sloth", 300, true);
	      data.addRowFromValues("Leopard", "http://en.wikipedia.org/wiki/Leopard", 50, false);
	      data.addRowFromValues("Tiger", "http://en.wikipedia.org/wiki/Tiger", 80, false);
	    } catch (TypeMismatchException e) {
	      System.out.println("Invalid type!");
	    }
	    return data;
	  }
	 @Override
	  protected boolean isRestrictedAccessMode() {
	    return false;
	  }

}
