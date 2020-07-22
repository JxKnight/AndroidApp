package com.example.chua.gogreen;

public class CategoriesLayout {
    public static String getColor(String categories)
    {
        String cc="";
        if(categories.equals("Utilities")) {cc="red";}
        if(categories.equals("Transportation")) {cc="red_dark";}
        if(categories.equals("Shopping")) {cc="green_dark";}
        if(categories.equals("Travel")) {cc="green";}
        if(categories.equals("Health")) {cc="blue";}
        if(categories.equals("Insurances")) {cc="dark_blue";}
        if(categories.equals("Education")) {cc="darkviolet";}
        if(categories.equals("Others")) {cc="violet";}
        return cc;
    }

    public static String getImage(String categories)
    {
        String image="";
        if(categories.equals("Utilities")) {image="utilities";}
        if(categories.equals("Transportation")) {image="transportation";}
        if(categories.equals("Shopping")) {image="shopping";}
        if(categories.equals("Travel")) {image="travel";}
        if(categories.equals("Health")) {image="health";}
        if(categories.equals("Insurances")) {image="insurances";}
        if(categories.equals("Education")) {image="education";}
        if(categories.equals("Others")) {image="others";}
        return image;
    }
}
