package CookieFac;

import CookieFac.models.Cookie;
import CookieFac.models.Material;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.*;



public class FactoryRunner {

    private static FactoryManager factoryManager;
    private static InventoryManager inventoryManager;

    public static void main(String[] args) {
        factoryManager = FactoryManager.getInstance();
        inventoryManager = InventoryManager.getInstance();
        Timer timer = new Timer();
        TimerTask exitApp = new TimerTask() {
        	String manufactured="";
            public void run() {
                
                for(Cookie cookie:factoryManager.getManufacturedCookies()){
                    manufactured+=cookie.getCode()+" "+cookie.getQuantity()+" ";
                }

                System.out.println("\nTotal Manufactured ["+factoryManager.getTotalManufactured()+"] "+manufactured);
                System.out.println("Total Dispatched "+FactoryManager.totalDispatched);
                System.out.println("Effective throughput "+factoryManager.getEffectiveThroughput());
                System.out.println("Number of invoices "+factoryManager.numberOfInvoice);
                facoryPrint();
                System.exit(0);
            }

			private void facoryPrint() {
				try{
		        	
		        	SimpleDateFormat DandT = new SimpleDateFormat ("dd.MM.yyyy  hh:mm:ss");
			        SimpleDateFormat T = new SimpleDateFormat ("hh:mm:ss"); 
		            
		                PrintWriter writer = new PrintWriter("Report.txt", "UTF-8");
		                writer.println("\n\n");
		                writer.println("--------------------------------------------------------------------------------+");
		                writer.println("               "+factoryManager.getFactory().getName().toUpperCase()+" OPERATION REPORT\t\t\t\t|");
		                writer.println("--------------------------------------------------------------------------------+");
		                writer.println("    Start time\t\t\t\t: "+DandT.format(new Date( ))+"\t\t\t|");
		                writer.println("    Production Line \t\t\t: "+FactoryManager.productionLine+"\t\t\t\t\t|");
		                writer.println("    Run Time\t\t\t\t: "+factoryManager.getRunTime()+"s\t\t\t\t\t|");
		                writer.println("    Warehouse Storage Capacity\t\t: "+inventoryManager.getWarehouse().getStorageCapacity()+"      \t\t\t\t|");
		                writer.println("    Factory Storage Capacity\t\t: "+factoryManager.getFactory().getStorageCapacity()+"     \t\t\t\t|");

		                writer.println("--------------------------------------------------------------------------------");        
		                writer.println("\n\n");
		                
		                
		                writer.println("-----Warehouse Inventory-----");
		                for (Material material:inventoryManager.getWarehouse().getMaterials()){
		                	if(material.getName().equals("Flour")){
		                		writer.println("Flour\t\t- "+material.getQuantity()+"Kg");
		                	}else if(material.getName().equals("Butter")){
		                		 writer.println("Butter\t\t- "+material.getQuantity()+"Kg");
		                	}else if(material.getName().equals("Chocolate Chips")){
		                		writer.println("Chocolate Chips- "+material.getQuantity()+"Kg");
		                	}else if(material.getName().equals("Milk")){
		                		writer.println("Milk\t\t- "+material.getQuantity()+"L");
		                	}else if(material.getName().equals("Suggar")){
		                		writer.println("Suggar\t\t- "+material.getQuantity()+"Kg");
		                	}else if(material.getName().equals("Ginger")){
		                		writer.println("Ginger\t\t- "+material.getQuantity()+"Kg");
		                	}
		                }
		                writer.println("\n\n");
		         /*       writer.println("-----Merchandise Inventory-----");
		                
		                for (Cookie cookie:inventoryManager.getWarehouse(). getCookies()){
		                	
		                	if(cookie.getName().equals("Base Biscuit")){
		                		writer.println("Base Biscuit\t\t- "+cookie.getQuantity()+"Kg");
		                	}else if(cookie.getName().equals("Butter Cookies")){
		                		writer.println("Butter Cookies\t\t- "+cookie.getQuantity()+"Kg");
		                	}else if(cookie.getName().equals("Chocolate Chip Cookies")){
		                		writer.println("Chocolate Chip Cookies\t\t- "+cookie.getQuantity()+"Kg");
		                	}else if(cookie.getName().equals("Ginger Cookies")){
		                		writer.println("Ginger Cookies\t\t- "+cookie.getQuantity()+"Kg");
		                	}
		                		writer.println("\n\n");
		                		writer.println("\n\nTotal Manufactured ["+factoryManager.getTotalManufactured()+"] "+manufactured);
			                	writer.println("Total Dispatched "+FactoryManager.totalDispatched);
			                	writer.println("Effective throughput "+factoryManager.getEffectiveThroughput());
				                writer.close();
		                
		         
		                }*/
		               
		        }catch(Exception ex){
		            
		        }
				
			}
        };

        for (String s : args) {    
            readFiles(s);
        }
        timer.schedule(exitApp, new Date(System.currentTimeMillis()+factoryManager.getRunTime()*1000));

        SimpleDateFormat date = new SimpleDateFormat("dd.MM.yyyy  hh:mm:ss");
        System.out.println("\n\n");
        System.out.println("+-----------------------------------------------------------------------+");
        System.out.println("|               "+factoryManager.getFactory().getName().toUpperCase()+" OPERATION REPORT \t\t\t|");
        System.out.println("+-----------------------------------------------------------------------+");
        System.out.println("|\t\t\t\t\t\t\t\t\t|");
        System.out.println("|    Start time \t\t             : "+date.format(new Date( ))+"\t|");
        System.out.println("|    Production Line \t \t\t     : "+FactoryManager.productionLine+"\t\t\t|");
        System.out.println("|    Run Time\t\t                     : "+factoryManager.getRunTime()+"s\t\t\t|");
        System.out.println("|    Warehouse Storage Capacity              : "+inventoryManager.getWarehouse().getStorageCapacity()+"      \t\t|");
        System.out.println("|    Factory Storage Capacity                : "+factoryManager.getFactory().getStorageCapacity()+"      \t\t|");
        System.out.println("|\t\t\t\t\t\t\t\t\t|");
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("\n\n");

        for (Material material:inventoryManager.getWarehouse().getMaterials()){
        	if(material.getName().equals("Chocolate Chips")){
        		System.out.println(material.getName()+" - "+material.getCode());
        	}else{
        		System.out.println(material.getName()+" \t\t- "+material.getCode());
        	}
            
        }
        System.out.println();
    }

    public static void readFiles(String fileName) {
        List<String> separators = new ArrayList<String>();
        separators.add("#base data");
        separators.add("#recipe line");
        separators.add("#warehouse inventory");
        separators.add("#Merchandise inventory");
        String section = "";
        String baseDir = System.getProperty("user.dir");
        
        try {
        	
            Reader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String data;
            while ((data = bufferedReader.readLine()) != null) {
                if (fileName.equals("cookieFactory.info")) {
                    if (separators.contains(data)) {
                        section = data;
                        continue;
                    } else {
                        if (section.equals(separators.get(0))) {
                            String[] splittedData = data.split(":");
                            if (splittedData[0].equals("Name")) {
                                factoryManager.getFactory().setName(data.split(": ")[1]);

                            } else if (splittedData[0].equals("Production line")) {
                                FactoryManager.productionLine=Integer.parseInt(data.split(": ")[1]);


                            } else if (splittedData[0].equals("Warehouse storage capacity")) {
                                inventoryManager.getWarehouse().setStorageCapacity(Integer.parseInt(data.split(": ")[1]));

                            } else if (splittedData[0].equals("Factory storage capacity")) {
                                factoryManager.getFactory().setStorageCapacity(Integer.parseInt(splittedData[1].trim()));

                            } else if (splittedData[0].equals("Run time")) {
                            	
                            	String x[]=data.split(":");
                            	String y=x[1].replaceAll("ms", "");
                            	factoryManager.setRunTime(Integer.parseInt(y));
                              
                            }
                        } else if (section.equals(separators.get(1))) {
                            factoryManager.addRecipeLine(data);
                        } else if (section.equals(separators.get(2))) {
                            Material material = inventoryManager.getMaterial(data);
                            inventoryManager.getWarehouse().getMaterials().add(material);

                        } else if (section.equals(separators.get(3))) {
                            inventoryManager.addCookies(data);
                        }
                    }
                } else if (fileName.equals("invoice.dat")) {
                    factoryManager.addInvoice(data);
                } else if (fileName.equals("rmarrival.dat")) {
                    inventoryManager.addRawMaterial(data);

                }
            }

            bufferedReader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}

