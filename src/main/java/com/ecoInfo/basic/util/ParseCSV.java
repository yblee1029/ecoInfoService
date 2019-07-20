package com.ecoInfo.basic.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvFactory;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

public class ParseCSV {

    public static <T> List<T> read(Class<T> responseClass, InputStream is) throws IOException {
    	
    	CsvFactory csvFactory = new CsvFactory();
        csvFactory.enable(com.fasterxml.jackson.dataformat.csv.CsvParser.Feature.TRIM_SPACES);
        csvFactory.configure(JsonParser.Feature.ALLOW_YAML_COMMENTS, true);
        CsvMapper csvMapper = new CsvMapper(csvFactory);
        CsvSchema schema = csvMapper.schemaFor(responseClass).withHeader();
//      ObjectReader reader = csvMapper.readerFor(responseClass).with(schema);   
        
        MappingIterator<T> mappingIterator = csvMapper.readerFor(responseClass).with(schema).readValues(new InputStreamReader(is, "euc-kr"));

        return mappingIterator.readAll();
    }
	
}
