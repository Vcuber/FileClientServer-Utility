import java.awt.Color;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.net.Socket;
import java.util.*;
import javax.swing.*;
import javax.swing.text.MaskFormatter;


//Commented out a JSON Implementation for storing port values

public class App {

    JFrame frame;
    MaskFormatter mf;
    JFormattedTextField t2;
    //Set<String> s;
    int asd = 0;
    List<String> lines = new ArrayList<String>();
    String[] arrayOfStrings;
    long bytesCopied = 0;
    long lenfi;
    JProgressBar jbar;
    JLabel l4;

    public App() {
        prepareGUI();
    }

    public static void main(String[] args) throws Exception {
        new App();
    }

    //Function to convert a Set of Strings to an Array of Strings
    // public static String[] convert(Set<String> setOfString)
    // {
    //     String[] arrayOfString = new String[setOfString.size()];
    //     int index = 0;
    //     for (String str : setOfString) {
    //         arrayOfString[index++] = str;
    //     }

    //     return arrayOfString;
    // }

    public void fileRead() {
        File f1 = new File("info.txt");
        try {
            Scanner op = new Scanner(f1);
            while(op.hasNextLine()) {
                lines.add(op.nextLine());
            }
            arrayOfStrings = lines.toArray(new String[0]);
            op.close();

        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static String[] last5Strings(String[] arrayOfStrings) throws ArrayIndexOutOfBoundsException {
        
        String[] arrayOf5Strings = new String[5];
        int j = arrayOfStrings.length -1;

        for(int i=0; i<5; i++) {
            arrayOf5Strings[i] = arrayOfStrings[j--];
        }

        return arrayOf5Strings;
    }

    // Consists of all the logic in the Frontend including swings implementation.
    public void prepareGUI() {
        // JSONObject jobj = new JSONObject();
        // JSONArray jarr = new JSONArray();
        // s = new TreeSet<String>();

        fileRead();

        try {
            File f2 = new File("ip.txt");
            if(!f2.exists()) {
                    f2.createNewFile();
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        //Creating a Frame
        frame = new JFrame("Title of the Window");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        //File and Text component 
        JButton b1 = new JButton("File");
        JTextField t1 = new JTextField();
        b1.setBounds(10, 20, 80, 30);
        t1.setBounds(100, 20, 275, 30);
        frame.add(b1);
        frame.add(t1);

        final JFileChooser fileDialog = new JFileChooser();

        // File Upload Action Listener
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fileDialog.showOpenDialog(frame);

                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    java.io.File file = fileDialog.getSelectedFile();
                    t1.setText(file.getAbsolutePath());
                }
                else {
                    JOptionPane.showMessageDialog(frame, "File not found", "File Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //IP Label and Text Component
        JLabel l1 = new JLabel("IP");
        try {
            
            
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        try {
            File f2 = new File("ip.txt");
            Scanner sc = new Scanner(f2);
            mf = new MaskFormatter("***.***.***.***");
            mf.setPlaceholderCharacter('x');
            t2 = new JFormattedTextField(mf);
            t2.setColumns(12);
            t2.setValue(sc.next());
            sc.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        l1.setBounds(50, 60, 80, 30);
        t2.setBounds(100, 60, 150, 30);
        frame.add(l1);
        frame.add(t2);

        //Port Label and Text Component
        JLabel l2 = new JLabel("PORT");
        // String[] convst = convert(s);

        String[] comboboxString = last5Strings(arrayOfStrings);
        // JComboBox to display the user the history of port values to either type or select from
        JComboBox<String> j2 = new JComboBox<>(comboboxString);
        j2.setEditable(true);
        j2.setMaximumRowCount(5);
        
        // JTextField t3 = new JTextField();
        // JComboBox action listener for autofill
        j2.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent ke) {
                if(ke.getKeyCode() == KeyEvent.VK_BACK_SPACE || ke.getKeyCode() == KeyEvent.VK_DELETE) {

                }
                else {

                String checking = (String) j2.getSelectedItem();
                int sugglength = checking.length();
                for(String data:arrayOfStrings) {
                    String checkfrom = "";
                    for(int i=0; i<sugglength; i++) {
                        if(sugglength<=data.length()) {
                            checkfrom = checkfrom+data.charAt(i);
                        }
                    }

                    // Autosuggestion works only in JTextField logic below
                    // if(checkfrom.equals(checking)) {
                    //     t3.setText(data);
                    //     t3.setSelectionStart(sugglength);
                    //     t3.setSelectionEnd(data.length());
                    //     break;
                    // }
                }
            }
            }
        });
        try {
            
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        j2.setBounds(100, 100, 150, 30);
        l2.setBounds(50, 100, 80, 30);
        
        // t3.setBounds(100, 100, 150, 30);
        frame.add(l2);
        // frame.add(t3);
        frame.add(j2);

        //ProgressBar
        UIManager.put("ProgressBar.background", Color.WHITE);
        UIManager.put("ProgressBar.foreground", Color.GREEN);
        JLabel l3 = new JLabel("Progress");
        l4 = new JLabel("");
        jbar = new JProgressBar(0, 100);
        l3.setBounds(10, 150, 80, 30);
        jbar.setBounds(110, 150, 200, 30);
        l4.setBounds(330, 150, 80, 30);
        jbar.setValue(0);
        jbar.setStringPainted(false);
        frame.add(jbar);
        frame.add(l3);
        frame.add(l4);
        
        //Send and Cancel Buttons
        JButton b2 = new JButton("Send");
        JButton b3 = new JButton("Cancel");
        b2.setBounds(10, 200, 80, 30);

        //Send Button ActionListener to start a connection and send files via ip and port and progress logic
        b2.addActionListener(e1 -> {

            String remStr = (String) t2.getValue();

            // IPAddress value
            // Replaces placeholder from maskformatter with no space to give a definite IP String.
            String str = remStr.replace("x", "");


            File f = new File("info.txt");
            
            //Port Value
            String portText = (String) j2.getSelectedItem();
            int portValue = Integer.parseInt(portText);
            
            try {
                FileWriter fw = new FileWriter(f, true);
                Scanner output = new Scanner(f);
                if(!f.exists()) {
                    f.createNewFile();
                }
                else {
                    while(output.hasNext()) {
                        String che = output.next();
                        if(che.equals(portText)) {
                            ++asd;
                            break;
                        }
                    }
                    if(asd==0) {
                        fw.write(portText+"\n");
                    }
                    else {
                        asd=0;
                    }
                }
                fw.close();
                output.close();
            }
            catch(Exception e) {
                e.printStackTrace();
            }

            try {
                File f2 = new File("ip.txt");
                FileWriter fww1 = new FileWriter(f2);
                BufferedWriter bw1 = new BufferedWriter(fww1);
                bw1.write((String) t2.getValue());
                bw1.close();
                fww1.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                SwingWorker<Integer, Void> worker = new SwingWorker<Integer,Void>(){
                    
                    public Integer doInBackground() {
                        try {
                            //Send file logic 
                            Socket sock = new Socket(str, portValue);
                            System.out.println("Client sending to: ");
                            System.out.println("IP: "+str);
                            System.out.println("PORT: "+portValue);
                            File myFile = new File(t1.getText());
                            String filename = myFile.getName();
                            //byte[] mybytearray = new byte[(int) myFile.length()];
                            byte[] buffer = new byte[1024];

                            // Storing file length for other use
                            lenfi = myFile.length();
                            FileInputStream fis = new FileInputStream(myFile);
                            BufferedInputStream bis = new BufferedInputStream(fis);
                            //bis.read(mybytearray, 0, mybytearray.length);
                            OutputStream os = sock.getOutputStream();
                            DataOutputStream dos = new DataOutputStream(os);
                            dos.writeUTF(filename);
                            //dos.writeLong(mybytearray.length);
                            //System.out.println(mybytearray.length);
                            int count = 0;
                            jbar.setMinimum(0);
                            jbar.setMaximum(100);
                            while((count = bis.read(buffer))>0) {
                                dos.write(buffer, 0, count);
                                bytesCopied += count;
                                int progressVal = (int) Math.round(((double) bytesCopied/ (double) lenfi)*100); 
                                System.out.println(progressVal);
                                jbar.setValue(progressVal);
                                l4.setText(progressVal+"%");
                            }
                            try {
                                Thread.sleep(500);
                            }
                            catch(InterruptedException e) {
                            }
                            dos.flush();
                            sock.close();
                            bis.close();
                        }
                        catch(Exception e) {
                            e.printStackTrace();
                        }

                        return 0;
                    }

                    public void done() {
                        JOptionPane.showMessageDialog(frame, "File has been transferred");
                        frame.dispose();
                        prepareGUI();
                    }
                };

                worker.addPropertyChangeListener(new PropertyChangeListener(){
                    public void propertyChange(PropertyChangeEvent event) {
                        if("progress".equals(event.getPropertyName())) {
                            jbar.setValue((Integer)event.getNewValue());
                        }
                    }
                });
                worker.execute();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        });

        b3.setBounds(300, 200, 80, 30);

        // Closes frame post click on cancel
        b3.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "The file transfer has been aborted");
            frame.dispose();
        });

        frame.add(b2);
        frame.add(b3);

        frame.setLayout(null);
        frame.setVisible(true);

    }
}

//JSON write implementation

// try {
            //     if(!f.exists()) {
            //         f.createNewFile();
            //     }
            //     jarr.put("port", t3.getText());
            //     FileWriter pw = new FileWriter("info.json", true);
            //     pw.write(jobj.toJSONString());
            //     pw.flush();
            //     pw.close();
            // }
            // catch(Exception e) {
            //     e.printStackTrace();
            // }
            // s.add(t3.getText());

// while((count = bis.read(buffer))>0) {
                //     dos.write(buffer, 0, count);
                //     bytesCopied += count;
                //     int progressVal = (int) Math.round(((double) bytesCopied/ (double) lenfi)*100); 
                //     System.out.println(progressVal);
                // }
                // new Thread(new Runnable(){
                //     public void run() {
                //         try {
                //             //Send file logic 
                //             Socket sock = new Socket(str, portValue);
                //             System.out.println("Client sending to: ");
                //             System.out.println("IP: "+str);
                //             System.out.println("PORT: "+portValue);
                //             File myFile = new File(t1.getText());
                //             String filename = myFile.getName();
                //             //byte[] mybytearray = new byte[(int) myFile.length()];
                //             byte[] buffer = new byte[1024];

                //             // Storing file length for other use
                //             lenfi = myFile.length();

                //             FileInputStream fis = new FileInputStream(myFile);
                //             BufferedInputStream bis = new BufferedInputStream(fis);
                //             //bis.read(mybytearray, 0, mybytearray.length);
                //             OutputStream os = sock.getOutputStream();
                //             DataOutputStream dos = new DataOutputStream(os);
                //             dos.writeUTF(filename);
                //             //dos.writeLong(mybytearray.length);
                //             //System.out.println(mybytearray.length);
                //             int count = 0;
                //             jbar.setMinimum(0);
                //             jbar.setMaximum(100);
                //                     while((count = bis.read(buffer))>0) {
                //                                 dos.write(buffer, 0, count);
                //                                 bytesCopied += count;
                //                                 int progressVal = (int) Math.round(((double) bytesCopied/ (double) lenfi)*100); 
                //                                 System.out.println(progressVal);
                //                                 jbar.setValue(progressVal);
                //                                 l4.setText(progressVal+"%");
                //                         }
                                        
                //                         try {
                //                             Thread.sleep(500);
                //                         }

                //                         catch(InterruptedException e) {
                //                         }
                //                 dos.flush();
                //                 sock.close();
                //                 bis.close();
                //             }
                //             catch(Exception e) {
                //                 e.printStackTrace();
                //             }
                //     }
                // }).start();
                // dos.flush();
                // sock.close();
                // bis.close();
