import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.swing.text.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

public class TextUI {
    JFrame UI=new JFrame();
    JTextArea text=new JTextArea();//设置文本显示框
    JScrollPane TextScroll=new JScrollPane(text);
    public TextUI(){
        UI.setTitle("纯文本");
        UI.setSize(640,800);
        UI.setLocationRelativeTo(null);// 设置窗体相对于另一个组件的居中位置，参数null表示窗体相对于屏幕的中央位置
        UI.setResizable(false);//可否让用户调大小
        UI.setLayout(null);

        text.setEditable(false);
        text.setLineWrap(true);
        text.setBounds(20,20,600,720);
        TextScroll.setBounds(20,20,600,720);
        TextScroll.setViewportView(text);
        TextScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        UI.add(TextScroll);

        UI.setVisible(true);
    }
    /*读取指定文件名的文件，并将文本提取到窗口显示，并高亮敏感词*/
    public void ExtractText(String filename) throws IOException,BadLocationException {
        File file=new File("..\\Internet\\html\\"+filename);
        BufferedReader BR=new BufferedReader(new FileReader(file));
        StringBuilder temp=new StringBuilder();

        String OneLine="";
        while((OneLine=BR.readLine())!=null){
            temp.append(OneLine);
        }
        BR.close();
        String finish=new String(temp);

        finish=finish.replaceAll("<[a-zA-Z]+.*?>([\\s\\S]*?)</[a-zA-Z]*>", "");//正则表达式清标签
        //finish=finish.replaceAll("</[a-zA-Z_0-9]*>","");
        //finish=finish.replaceAll("<![^>]*>","");
        //finish=finish.replace("&nbsp","");
        finish=finish.replaceAll(" +", " ").replaceAll("\t+","\t");//对于多个空格，只保留一个
        //finish=finish.replaceAll("\n+","\n").replaceAll("\r+","\r");

        text.append(finish);
        /*关键字入List,对关键字高亮显示*/
        List<String>KeyWord=new ArrayList<>();
        File SensitiveFile=new File("..\\Internet\\敏感词库.txt");
        InputStreamReader stream=new InputStreamReader(new FileInputStream(SensitiveFile),"UTF-8");
        BufferedReader in=new BufferedReader(stream);
        while((OneLine=in.readLine())!=null){
            KeyWord.add(OneLine);
        }
        in.close();
        Highlighter HL= text.getHighlighter();
        String dispose=text.getText();//拿字符串进行匹配，再对原文操作
        DefaultHighlighter.DefaultHighlightPainter Light=new DefaultHighlighter.DefaultHighlightPainter(Color.RED);
        for(String key:KeyWord){
            int pos=0;
            while((pos=dispose.indexOf(key,pos))>=0) {//如果能找到匹配串

                HL.addHighlight(pos, pos + key.length(), Light);    //高亮显示匹配到的词语
                pos += key.length();    //更新匹配条件
            }
        }
        //关闭
    }
}
