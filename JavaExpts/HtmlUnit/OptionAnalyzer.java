import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import com.google.common.io.*;
import com.google.common.base.Charsets;


public class OptionAnalyzer {

  static String[] symbols = 
    {
        // "http://finance.yahoo.com/q/op?s=spy&m=2012-03",
        // "http://finance.yahoo.com/q/op?s=qqq&m=2012-03",
        "http://finance.yahoo.com/q/op?s=aapl&m=2012-03",
    };

  static List<String> symbolsList = Arrays.asList(symbols);

  public static void main(String [] args) {
    try {
      while ( true ) {
        for ( String s : symbols ) { 
          List<String> lines = OptionScraper.scrapeFromUrl( s );
          StringBuffer sb = new StringBuffer();
          for ( String tmp : lines ) {
            sb.append(tmp + "\n");
          }
          List<Option> options = OptionParse.parse(sb.toString());
          analyze( options );
        }
	System.out.println(new Date().toString());
        Thread.sleep(1000);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void analyze( List<Option> options) {
    List<OptionValue> l = new ArrayList<OptionValue>();
    for ( Option opt : options ) {
      if ( opt.isCall ) {
        l.add( new OptionValue( opt, ( opt.strike - opt.stockPrice ) ) );
      }
    }
    Collections.sort( l );
    int i = 0;
    for ( OptionValue ov :  l ) {
      System.out.println(i++ + " " + ov.toString() );
      if ( i > 10 ) {
        break;
      }
    }
  }
}