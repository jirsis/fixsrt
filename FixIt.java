import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class FixIt {
	
	private static String EXTENSION=".srt";
	private static String FILENAME_PREFIX_FIXED = "-fixed"+FixIt.EXTENSION;
	
	private static String ENCODING="8859_1";
	
	private String fileName;
	private int seconds_delay;
	
	public FixIt(String ... args){
		fileName=args[0].replace(FixIt.EXTENSION, "");
		seconds_delay=Integer.parseInt(args[1]);
	}
	
	public void fix() throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName+FixIt.EXTENSION), FixIt.ENCODING));
		BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName+FixIt.FILENAME_PREFIX_FIXED), FixIt.ENCODING));
		String linea = br.readLine();
		while (linea != null) {
			if (linea.contains("-->")) {
				output.write(fixLine(linea));
			} else {
				output.write(linea);
			}
			output.write("\n");
			linea = br.readLine();
		}
		br.close();
		output.close();
	}

	private String fixLine(String linea) {
		String separador = " --> ";
		Calendar horaInicioCal = formatDate(linea.split(separador)[0]);
		Calendar horaFinCal = formatDate(linea.split(separador)[1]);
		return printHora(horaInicioCal) + separador + printHora(horaFinCal);
	}

	private String printHora(Calendar horaInicioCal) {
		SimpleDateFormat formater = new SimpleDateFormat("HH:mm:ss,SSS");
		return formater.format(horaInicioCal.getTime());
	}

	private Calendar formatDate(String horaInicio) {
		Calendar hora = GregorianCalendar.getInstance();
		String horaTroceada[] = horaInicio.split(":");
		hora.set(Calendar.HOUR_OF_DAY, Integer.parseInt(horaTroceada[0]));
		hora.set(Calendar.MINUTE, Integer.parseInt(horaTroceada[1]));
		hora.set(Calendar.SECOND, Integer.parseInt(horaTroceada[2].split(",")[0]));
		hora.set(Calendar.MILLISECOND, Integer.parseInt(horaInicio.split(",")[1]));
		hora.add(Calendar.SECOND, seconds_delay);
		return hora;
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		new FixIt(args).fix();
	}


}
