/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spanningtreeprotocol;

/**
 *
 * @author qib
 */
public class SpanningTreeProtocol {

    static String S1 = "81 0 81", S2 = "41 19 125", S3 = "41 12 315", S4 = "41 12 111", S5 = "41 13 90";
    static int s = 5; //total number of switches
    static String [] ports = new String[s]; //total number of ports
    static int [] rootID = new int [s], costs = new int [s], transID = new int[s]; 
    static int switches = 0;
    static int bpdurootbridge = Integer.MAX_VALUE;
    static int bpducost = Integer.MAX_VALUE;
    static int bpdutrans = Integer.MAX_VALUE;   
    static int bridgeID = 92; //pre-defined bridge id
    static String BPDU;     
    
    //splitting method to read RootID, CostToRoot, TransmittingBridgeID
    public void split(String S, int num){  
        String [] splitBPDU = S.split(" ");
//        for (int i = 0; i < splitBPDU.length; i++){
//            System.out.print(" " + splitBPDU[i]);
//            
//        }    
        rootID[num] = Integer.parseInt(splitBPDU[0]);
        costs[num] = Integer.parseInt(splitBPDU[1]);
        transID[num] = Integer.parseInt(splitBPDU[2]);     
        switches++;
    }
    
    public void rootPort(int [] rootID, int [] costs, int [] transID){        
        int temp = 0;        
        for (int i=0; i<rootID.length; i++){            
               if (rootID[i]<bpdurootbridge)
                   bpdurootbridge = rootID[i];                
        }
        
        for (int i = 0 ; i <rootID.length; i++){
        if (rootID[i] == bpdurootbridge)                        
                   if (costs[i]<bpducost)
                       bpducost = costs[i];                       
        }        
        for (int i = 0 ; i <rootID.length; i++){
        if (rootID[i] == bpdurootbridge && costs[i] == bpducost){                         
                   if (transID[i]<bpdutrans){
                       bpdutrans = transID[i]; 
                       temp = i;                       
                   }                  
                   
              }
        }
        ports[temp] = "Root Port";
        System.out.println("Root Bridge: " + bpdurootbridge); 
        
    }
    
    public String BPDU(){
        bpducost = bpducost+1;
        bpdutrans = bridgeID;
        BPDU = bpdurootbridge + " " + bpducost + " " + bpdutrans;
        return BPDU;
    }

    public void designatedPort(int [] rootID, int [] costs, int [] transID){ 
        
        for (int i=0; i<rootID.length; i++){
            if (rootID[i] > bpdurootbridge){
                ports[i] = "Designated Port";
            }
            else if ((rootID[i] == bpdurootbridge) && (costs[i] > bpducost)){
                ports[i] = "Designated Port";
            }
            else if ((rootID[i] == bpdurootbridge) && (costs[i] == bpducost) && (transID[i] > bpdutrans)){
                ports[i] = "Designated Port";
            }
        }        
        
    }
    public void blockedPort(int [] rootID, int [] costs, int [] transID){       
       
        for (int j = 0; j < ports.length; j++){
            if (ports[j] == null){
                ports[j] = "Blocking Port";
            }
        }        
        
    }
    public static void main(String args[]) {
        // TODO code application logic here
         SpanningTreeProtocol stp = new SpanningTreeProtocol();
        
         stp.split(S1,switches);
         stp.split(S2,switches);
         stp.split(S3,switches);
         stp.split(S4,switches);
         stp.split(S5,switches);
        
         System.out.println("BPDUs received: ");
         System.out.println("Switch 1: " + S1);
         System.out.println("Switch 2: " + S2);
         System.out.println("Switch 3: " + S3);
         System.out.println("Switch 4: " + S4);
         System.out.println("Switch 5: " + S5);
         
         System.out.println();         
         System.out.println("Total number of switches: " + s);
         System.out.println();
         System.out.println("Total number of ports: " + s); 
         System.out.println();
         System.out.println("Bridge ID: " + bridgeID); 
         System.out.println();
         
         
         stp.rootPort(rootID, costs, transID);
         
         System.out.println();
         System.out.println("BPDU : " + stp.BPDU());
         
         stp.designatedPort(rootID, costs, transID);
         stp.blockedPort(rootID, costs, transID);         
         
         System.out.println();
         
         for (int i = 0; i < 5; i++){
             System.out.println("Port " + (i+1) + ": ");
             System.out.print(" " + ports[i]);
             System.out.println();
         }
         
    }
}
