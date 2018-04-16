package com.javarush.task.task33.task3308;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType(name = "shop")
@XmlRootElement
public class Shop {
    public Goods goods;
    public int count;
    public double profit;
//    @XmlAnyElement
    public String[] secretData;

    public static class Goods {
        public List<String> names;
    }

    @Override
    public String toString() {
        String goodsString = "Goods:\n";
        for (int i = 0; i < goods.names.size(); i++) {
            goodsString += goods.names.get(i) + "\n";
        }
        return "Shop:\n"
                    + goodsString
                    + "count = " + count + "\n"
                    + "profit = " + profit + "\n"

                + "}";

    }
}
