package com.isu.csvdatasource;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.*;
import org.apache.log4j.Logger;

import com.google.visualization.datasource.DataSourceHelper;
import com.google.visualization.datasource.DataSourceServlet;
import com.google.visualization.datasource.base.DataSourceException;
import com.google.visualization.datasource.base.ReasonType;
import com.google.visualization.datasource.datatable.DataTable;
import com.google.visualization.datasource.query.Query;
import com.google.visualization.datasource.util.CsvDataSourceHelper;
import com.ibm.icu.util.ULocale;

public class CsvDataSourceServlet  extends DataSourceServlet{
	
	/**
	   * Log.
	   */
	  //private static final Logger log = LogFactory.getLog(CsvDataSourceServlet.class.getName());
	  private static final Logger log = Logger.getLogger(CsvDataSourceServlet.class.getName());

	  /**
	   * This is a cooment to see the chnage in git
	   * The name of the parameter that contains the url of the CSV to load.
	   */
	  private static final String URL_PARAM_NAME = "url";

	  /**
	   * Generates the data table.
	   * This servlet assumes a special parameter that contains the CSV URL from which to load
	   * the data.
	   */
	  public DataTable generateDataTable(Query query, HttpServletRequest request)
	      throws DataSourceException {
		  System.out.println("genertae table function called");
	    String url = request.getParameter(URL_PARAM_NAME);
	    if (StringUtils.isEmpty(url)) {
	      log.error("url parameter not provided.");
	      throw new DataSourceException(ReasonType.INVALID_REQUEST, "url parameter not provided");
	    }

	    Reader reader;
	    try {
	      reader = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
	    } catch (MalformedURLException e) {
	      log.error("url is malformed: " + url);
	      throw new DataSourceException(ReasonType.INVALID_REQUEST, "url is malformed: " + url);
	    } catch (IOException e) {
	      log.error("Couldn't read from url: " + url, e);
	      throw new DataSourceException(ReasonType.INVALID_REQUEST, "Couldn't read from url: " + url);
	    }
	    DataTable dataTable = null;
	    ULocale requestLocale = DataSourceHelper.getLocaleFromRequest(request);
	    try {
	      // Note: We assume that all the columns in the CSV file are text columns. In cases where the
	      // column types are known in advance, this behavior can be overridden by passing a list of
	      // ColumnDescription objects specifying the column types. See CsvDataSourceHelper.read() for
	      // more details.
	      dataTable = CsvDataSourceHelper.read(reader, null, true, requestLocale);
	    } catch (IOException e) {
	      log.error("Couldn't read from url: " + url, e);
	      throw new DataSourceException(ReasonType.INVALID_REQUEST, "Couldn't read from url: " + url);
	    }
	    return dataTable;
	  }
	  /**
	   * NOTE: By default, this function returns true, which means that cross
	   * domain requests are rejected.
	   * This check is disabled here so examples can be used directly from the
	   * address bar of the browser. Bear in mind that this exposes your
	   * data source to xsrf attacks.
	   * If the only use of the data source url is from your application,
	   * that runs on the same domain, it is better to remain in restricted mode.
	   */
	  @Override
	  protected boolean isRestrictedAccessMode() {
	    return false;
	  }
}
