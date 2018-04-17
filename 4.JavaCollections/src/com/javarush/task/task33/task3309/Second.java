package com.javarush.task.task33.task3309;

import javax.xml.bind.annotation.XmlElement;

public class Second {
    @XmlElement(name = "second")
    public String item1 = "some string";
    @XmlElement(name = "second")
    public String item2 = "need CDATA because of <second>";
}