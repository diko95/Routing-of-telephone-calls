package com.Learning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.Scanner;

public class OperatorPriceCalculator2
{

    static double lowest = 0;
    static String currentOperatorName = "";
    public static void main( String[] args )
    {
        System.out.println("Enter number of operators");
        Scanner s = new Scanner(System.in);
        int noOfOperators =  s.nextInt();

        List<Map<String, String>> operatorsList = new ArrayList<>();

        for (int i=0; i < noOfOperators; i++) {
            Map<String, String> priceMap = new HashMap<>();

            if(i==0) {
                s.nextLine();
            }
            System.out.println("Please enter the name of the operator");
            String operatorName = s.nextLine();;
            priceMap.put("OperatorName", operatorName);

            System.out.println("Please enter price list size for this operator");
            int priceListSize = s.nextInt();
            System.out.println("please enter space separated \"telephone prefix\" and \"price per minute\" ");

            s.nextLine();
            for(int j=0; j < priceListSize; j++) {
                String price[] = s.nextLine().split(" ");
                priceMap.put(price[0], price[1]);
            }

            operatorsList.add(priceMap);
        }

        System.out.println("Please enter the mobile number");
        String mobileNumber = s.next();

        if(!operatorsList.isEmpty()) {
			int l2 = 0 ;
			String formattedMobileNumber = mobileNumber.replaceAll("\\+","").replaceAll("-","");
			Map<String, Double> lowestpricedOperator = new LinkedHashMap<>();
			for(Map<String,String> operator: operatorsList) {
				for(Entry<String, String> e : operator.entrySet()) {
					//System.out.println(e.getValue());
					if(formattedMobileNumber.startsWith(e.getKey()) ) {
						//System.out.println("yes its in : "+e.getValue());
						//System.out.println("lowest till now is: "+lowest);
						Double present = Double.parseDouble(e.getValue());
						if(e.getKey().length() == l2 && present < lowest) {
							lowest = Double.parseDouble(e.getValue());
							currentOperatorName = operator.get("OperatorName");
							lowestpricedOperator.put(currentOperatorName, lowest);
							//System.out.println("found for in first if :"+currentOperatorName +" price-:" +lowest);
						}
						else if(e.getKey().length() > l2  ) {
							lowest = Double.parseDouble(e.getValue());
							currentOperatorName = operator.get("OperatorName");
							lowestpricedOperator.put(currentOperatorName, lowest);
							//System.out.println("found for :"+currentOperatorName +" price-:" +lowest);
						}
						
						l2 = e.getKey().length();
					}

				}

				
			}

			StringBuffer operatorString = new StringBuffer("for dialing " + mobileNumber + " you would have to pay ");
			final Map<String, Double> sortedByCount = lowestpricedOperator.entrySet()
					.stream()
					.sorted((Map.Entry.<String, Double>comparingByValue()))
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
			Map.Entry<String, Double> entry = sortedByCount.entrySet().iterator().next();
			String key = entry.getKey();
			Double value = entry.getValue();

			operatorString.append(key + " $" + value + "/min, ");
			System.out.println(operatorString.toString());
		}
        else {
            System.out.println("please provide operators to calculate price");
        }



    }
}
