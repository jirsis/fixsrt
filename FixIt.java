import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream.GetField;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class FixIt {

	public static void main(String[] args) throws IOException, InterruptedException {

		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), "8859_1"));
		BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("fixed.srt"), "8859_1"));
		// PrintWriter output = new PrintWriter(new BufferedWriter(new
		// FileWriter("fixed.srt")));
		String linea = br.readLine();
		while (linea != null) {
			if (linea.contains("-->")) {
				output.write(fixLine(linea));
//				System.out.println(fixLine(linea));
			} else {
				// String lineaUtf = new String(linea.getBytes());
				output.write(linea);
//				System.out.println(linea);
			}
			output.write("\n");
			linea = br.readLine();
		}
		br.close();
		output.close();

	}

	private static String fixLine(String linea) {
		Calendar horaInicioCal = formatDate(linea.split(" --> ")[0]);
		Calendar horaFinCal = formatDate(linea.split(" --> ")[1]);
		return printHora(horaInicioCal) + " --> " + printHora(horaFinCal);
	}

	private static String printHora(Calendar horaInicioCal) {
		SimpleDateFormat formater = new SimpleDateFormat("HH:mm:ss,SSS");
		return formater.format(horaInicioCal.getTime());

	}

	private static Calendar formatDate(String horaInicio) {
		Calendar hora = GregorianCalendar.getInstance();
		String horaTroceada[] = horaInicio.split(":");
		hora.set(Calendar.HOUR_OF_DAY, Integer.parseInt(horaTroceada[0]));
		hora.set(Calendar.MINUTE, Integer.parseInt(horaTroceada[1]));
		hora.set(Calendar.SECOND, Integer.parseInt(horaTroceada[2].split(",")[0]));
		hora.set(Calendar.MILLISECOND, Integer.parseInt(horaInicio.split(",")[1]));

		hora.add(Calendar.SECOND, 45);
		return hora;
	}

}
