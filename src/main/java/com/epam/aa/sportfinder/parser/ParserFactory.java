//package com.epam.aa.sportfinder.parser;
//
//import com.epam.aa.sportfinder.config.AppProperties;
//import com.epam.aa.sportfinder.parser.xml.SaxParser;
//import com.epam.aa.sportfinder.parser.xml.StaxParser;
//
//public class ParserFactory {
//
//    public static Parser getParser() {
//        if (AppProperties.getParserProperty("parserType").equals("sax")) {
//            return new SaxParser();
//        } else if (AppProperties.getParserProperty("parserType").equals("stax")) {
//            return new StaxParser();
//        }
//        return null;
//    }
//}
