import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.swing.filechooser.*;
import javax.swing.text.BadLocationException;

public class FunctionUI {
    JFrame UI=new JFrame();
    /*javax.swing画UI*/
    public FunctionUI () {
        UI.setTitle("Spider");
        UI.setBackground(new Color(196,160,196));//灰色
        UI.setSize(600,180);
        UI.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//关闭窗口时，JVM一并退出
        UI.setLocationRelativeTo(null);// 设置窗体相对于另一个组件的居中位置，参数null表示窗体相对于屏幕的中央位置
        UI.setResizable(false);//可否让用户调大小
        UI.setLayout(null);

        JLabel hint=new JLabel("输入URL:");
        hint.setBounds(30,30,80,30);
        UI.add(hint);

        JTextField URLText=new JTextField();
        URLText.setBounds(95,30,450,30);
        UI.add(URLText);

        JButton DirectCatch=new JButton("直接爬取");
        DirectCatch.setBounds(100,90,150,30);
        JButton BatchCatch=new JButton("批量爬取");
        BatchCatch.setBounds(350,90,150,30);
        UI.add(DirectCatch);
        UI.add(BatchCatch);

        DirectCatch.addActionListener(AL->{
            if(!URLText.getText().equals("")){//有输入，下一步操作
                try{
                    Function DC=new Function(URLText.getText());
                    String msg = JOptionPane.showInputDialog(UI, "您要保存的文件名为",
                            "确定", JOptionPane.PLAIN_MESSAGE);
                    DC.GetHTML("..\\Internet\\html\\"+msg);
                    TextUI PlainText=new TextUI();
                    PlainText.ExtractText(msg);
                }catch(IOException | BadLocationException e){
                    e.printStackTrace();
                }
            }
        });

        BatchCatch.addActionListener(AL->{
            JFileChooser open = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(null, "txt");
            open.setFileFilter(filter);//过滤文件，只显示txt后缀
            int returnVal = open.showOpenDialog(open);//打开对话框
            if(returnVal == JFileChooser.APPROVE_OPTION) {//选择文件之后
                try{
                    int one;
                    String OneURL="";
                    String message=new String();
                    boolean IsInputFileName=false;
                    BufferedReader input=new BufferedReader(new FileReader(open.getSelectedFile()));
                    TextUI PT=new TextUI();
                    Function DC;
                    while((OneURL=input.readLine())!=null){//读到文件末尾才跳出循环
                            if(!IsInputFileName) {//没询问过就问一次
                                message = JOptionPane.showInputDialog(UI, "您要保存的文件名为",
                                        "确定", JOptionPane.PLAIN_MESSAGE);
                                IsInputFileName=true;
                            }
                            DC=new Function(OneURL);
                            DC.GetHTML("..\\Internet\\html\\"
                                    +message);
                            OneURL="";
                    }
                    PT.ExtractText(message);
                }catch (IOException | BadLocationException e){
                    e.printStackTrace();
                }
            }
        });

        UI.setVisible(true);
    }
}
