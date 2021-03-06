import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class final3Login {

    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("============= Loing ==============");

        String[][] account = Readfile();

        String ans  ;
        boolean check = true;
        boolean checkPass = false;
        String Inputusername = "";
        String Inputpassword = "";

        System.out.print ("You want to login (y/n) : ");
        ans = input.nextLine() ;

        if(ans.equals("y") || ans.equals("y"))  {

        while (check) {

            if (checkPass == false) {
                System.out.println("Plase Input username and password ");
                System.out.print("Input Username : ");
                Inputusername = input.nextLine();
                System.out.print("Input Password : ");
                Inputpassword = input.nextLine();

            }

            for (int i = 0; i < account.length; i++) {


                if ( Inputusername.equals(account[i][0]) && Encrytion(Inputpassword).equals(account[i][1])) {
                    checkPass = true;
                    check = false;
                    break;

                }
            }

        }
     }


        if (checkPass) {
            System.out.println("===== Loing pass!!!=====");
        }else {
            System.out.println("===== Loing fail!!!=====");
        }

    }

    static String Encrytion(String password) {


        ArrayList<String> password_array = new ArrayList<String>();

        for (int i = 0; i < password.toCharArray().length; i++) {
            password_array.add(String.valueOf(password.toCharArray()[i]));
        }

        /* Add Padding */
        if (password_array.size() % 3 != 0) {

            for (int i = 0; i < password_array.size() % 3; i++) {
                password_array.add("X");
            }
            System.out.println("Add Padding Result : " + password_array);
        }

        String passwordEncryp = "";

        for (int i = 0; i < password_array.size() / 3; i++) {

            int index = i * 3;

            String encodeText = Transposition(
                    SubstitionBit(Shift(XOR(convToBinary(password_array.get(index).charAt(0))
                            + convToBinary(password_array.get(index + 1).charAt(0))
                            + convToBinary(password_array.get(index + 2).charAt(0))))));
            passwordEncryp += encodeText;

        }

        return passwordEncryp ;
    }

    static String[][] Readfile () {

        
        ArrayList <String> accoutdata = new ArrayList<>();

        BufferedReader reader;
        try {
			reader = new BufferedReader(new FileReader("C:\\Users\\silal\\Desktop\\Account.txt"));
			String line = reader.readLine();
			while (line != null) {
				accoutdata.add(line) ;

				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

        String[][] list = new String [accoutdata.size()/2][2] ;


        for (int i = 0; i < list.length; i++) {
            for (int j = 0; j < list[i].length; j++) {
                list [i][j] = accoutdata.get(0) ;
                accoutdata.remove(0) ;
            }
        }


        return list ;
        
        
    }
  
    static String convToBinary(char message) {

        StringBuilder result = new StringBuilder();
          result.append( String.format("%8s", Integer.toBinaryString(message))   // char -> int, auto-cast
                            .replaceAll(" ", "0")) ;                        // zero pads

        return  result.toString();
    }

    static String ConvIntToBinary(int number) {

        return String.format("%8s", Integer.toBinaryString(number)).replace(' ', '0');
    }

    static String Shift(String message) {

        int key = 5; // KEY FOR SHIFT
        char[] shitfMessage = message.toCharArray();

        for (int i = 0; i < key; i++) {

            char temp = shitfMessage[shitfMessage.length - 1];

            for (int j = shitfMessage.length - 1; j > 0; j--) {
                shitfMessage[j] = shitfMessage[j - 1];
            }
            shitfMessage[0] = temp;
        }

        return String.valueOf(shitfMessage);

    }

    static String Transposition(String message) {

        int[] Key = { 7, 4, 3, 5, 2, 1, 6, 0, 11, 15, 13, 10, 12, 8, 9, 14 ,19 , 16 , 20 , 22 , 17 , 18 ,21 ,23 }; // KEY FOR TRANSPOSITION
        char[] message_array = message.toCharArray();
        char[] storeValue = { '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0' ,'0', '0', '0', '0' ,'0','0','0'};

        for (int i = 0; i < message_array.length; i++) {
            storeValue[i] = message_array[Key[i]];
        }

        return String.valueOf(storeValue);

    }

    static String SubstitionBit(String message) {


        String[] str_val = { message.substring(0, 12), message.substring(12, 24) };
        String[] col = { "0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111", "1000", "1001", "1010", "1011","1100", "1101", "1110", "1111" };
        String[] row = { "000", "001", "010", "100","101","110","111" };
        String[][] table = {
            {"0010", "1100", "0100", "0001", "0111", "1010", "1011", "0110", "1000", "0101", "0011", "1111", "1101", "0000", "1110", "1001"},
            {"1110", "1011", "0010", "1100", "0100", "0111", "1101", "0001", "0101", "0000", "1111", "1010", "0011", "1001", "1000", "0110"},
            {"0100", "0010", "0001", "1011", "1010", "1101", "0111", "1000", "1111", "1001", "1100", "0101", "0110", "0011", "0000", "1110"},
            {"1011", "1000", "1100", "0111", "0001", "1110", "0010", "1101", "0110", "1111", "0000", "1001", "1010", "0100", "0101", "0011"},
            {"1010", "1101", "0111", "1000", "0100", "0010", "0001", "1011", "1111", "1001", "1100", "0101", "0110", "0011", "0000", "1110"},
            {"1111", "0100", "0010", "0001", "1011", "1001", "0111", "1000", "0110", "0011", "0000", "1110", "1100", "0101", "1010", "1101"},
            {"1000", "1111", "1001", "1100", "0101", "0110", "0011", "0000", "1110", "0100", "0010", "0001", "1011", "1010", "1101", "0111"}
            };
            // KEY SubstitionBit

        ArrayList<ArrayList<Integer>> index = new ArrayList<>();

        for (int i = 0; i < str_val.length; i++) {

            ArrayList<Integer> match = new ArrayList<>();

            for (int j = 0; j < row.length; j++) {
                if (str_val[i].substring(0, 3).equals(row[j])) {
                    match.add(j);
                }
            }

            for (int j = 0; j < col.length; j++) {
                if (str_val[i].substring(8, 12).equals(col[j])) {
                    match.add(j);
                }
            }

            index.add(match);

        }

        String value = str_val[0].substring(0, 8) + table[index.get(0).get(0)][index.get(0).get(1)];
        String value2 = str_val[1].substring(0, 8) + table[index.get(1).get(0)][index.get(1).get(1)];

        return value + value2;
    }

    static String XOR(String message) {

        char[] message_array = message.toCharArray();
        char[] Key = { '1', '0', '1', '0', '0', '1', '0', '0', '1', '1', '0', '1', '0', '0', '1', '0' , '0' ,'1','1', '0', '0', '1', '1', '0' }; //KEY  XOR
        int[] val =  { '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0' };
        for (int i = 0; i < Key.length; i++) {
            val[i] = message_array[i] ^ Key[i];
        }
        String Text = "";
        for (int i = 0; i < val.length; i++) {
            Text += String.valueOf(val[i]);
        }

        return Text;
    }
  
    static String BinaryToString(String message) {

        String s = message;
        String str = "";

        for (int i = 0; i < s.length() / 8; i++) {

            int a = Integer.parseInt(s.substring(8 * i, (i + 1) * 8), 2);
            str += (char) (a);
        }

        return str;
    }



}
