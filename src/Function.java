import java.net.*;
import java.io.*;

public class Function {
    String input ="";
    public Function(String url){
        this.input =url;
    }
    /*获取对应网址的html文件，并保存在相应文件中*/
    public void GetHTML(String filename) throws IOException{
        URL url=new URL(input);
        URLConnection connect = url.openConnection();
        HttpURLConnection connection = null;
        String urlString = "";
        String OneLine;

        connection = (HttpURLConnection) connect;
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        File SaveFile=new File(filename);
        FileWriter output=new FileWriter(SaveFile,true);//追加方式写入
        //output.write("内容如下:\n");
        while((OneLine = in.readLine()) != null) {
            output.write(OneLine);
            output.write("\n");
        }
        output.write("\n\n");
        output.close();
        System.out.println(urlString);
    }
}
