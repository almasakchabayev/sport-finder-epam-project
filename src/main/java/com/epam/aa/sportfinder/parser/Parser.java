package com.epam.aa.sportfinder.parser;

import java.io.*;

public interface Parser {
    <E> E parse(InputStream inputStream);
    default <E> E parse(File file) throws FileNotFoundException {
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        return parse(inputStream);
    }
    //TODO: handle exception
    default <E> E parse(String fileName) throws FileNotFoundException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        return parse(file);
    }
}
