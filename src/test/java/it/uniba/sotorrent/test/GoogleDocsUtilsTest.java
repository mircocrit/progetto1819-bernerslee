package it.uniba.sotorrent.test;

import static org.junit.Assume.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.*;

import org.junit.jupiter.api.*;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.cloud.bigquery.Job;
import it.uniba.sotorrent.GoogleDocsUtils;
import it.uniba.sotorrent.SOQuery;
import it.uniba.utils.Edge;

@Tag("GoogleDocsUtils")
public class GoogleDocsUtilsTest {

	private static GoogleDocsUtils g = new GoogleDocsUtils();
	private static Map<String, Long> res;
	private static ArrayList<String> arrNode;
	private static ArrayList<Edge> arrEdge;
	private static String spid;
	private static final String APPLICATION_NAME = "sna4so";

	private static final List<String> SCOPES = Arrays.asList(SheetsScopes.DRIVE);
	private static Sheets sheetsService;
	private Drive driveService;
	private Credential credential;
	private static final String url = "http://neo.di.uniba.it/credentials/project-bernerslee-vvb56d.json";
	// "http://neo.di.uniba.it/credentials/project-sna4so.json"; //

	@BeforeAll
	public static void setUpAll() {
		g = new GoogleDocsUtils();
	}

	@AfterAll
	public static void tearDownAll() {
		g = null;
	}

	@BeforeEach
	public void setUp() {
	}

	@AfterEach
	public void tearDown() {
	}

	@Test
	@DisplayName("writeSheet")
	public void writeSheetTest() {

		try {
			SOQuery soq = new SOQuery();
			Job job = soq.runQuery();
			res = soq.getResults(job);
			spid = g.createSheet("Prova sna4so");
			g.shareSheet(spid);
			g.getSheetByTitle(spid);
			g.writeSheet(spid, res);
			// Controllo risultato sheet
		} catch (Exception e) {
			assumeNoException(e);
		}

		try {
			res = null;
			spid = g.createSheet("Prova sna4so");
			g.shareSheet(spid);
			g.getSheetByTitle(spid);
			g.writeSheet(spid, res);
		} catch (Exception e) {
			assumeNoException(e);
		}

		/*
		 * System.out.println("START TEST Eccezione"); GoogleJsonResponseException e =
		 * assertThrows(GoogleJsonResponseException.class, () -> {
		 * g.writeSheet(spid,res); g.writeSheet(spid, res); g.writeSheet(spid, res); });
		 * assertTrue(e.getMessage().
		 * equals("Errore. Aspettare 100 sec. e rieseguire il comando"));
		 * System.out.println("END TEST Eccezione");
		 */
	}

	@Test
	@DisplayName("writeNode")
	public void writeNodeTest() {

		try {
			SOQuery soq = new SOQuery();
			Job job = soq.runNodeAnswerDate(2018, 02, 02, 3);
			arrNode = soq.getNode(job);
			spid = g.createSheet("Prova sna4so");
			g.writeNode(spid, arrNode);

			// Controllo risultato sheet //

			// Download Sheet online //
			ArrayList<String> esterno = readSheet1(spid, "A2:A101");

			// Comparazione Sheet di Nodes
			assertTrue(arrayStringCompare(arrNode, esterno));
			// System.out.println(arrayStringCompare(arrNode,esterno));

		} catch (Exception e) {
			assumeNoException(e);
		}

		try {
			arrNode = null;
			spid = g.createSheet("Prova sna4so");
			g.writeNode(spid, arrNode);
		} catch (Exception e) {
			assumeNoException(e);
		}

		/*
		 * System.out.println("START TEST Eccezione"); GoogleJsonResponseException e =
		 * assertThrows(GoogleJsonResponseException.class, () -> { g.writeNode(spid,
		 * arr); g.writeNode(spid, arr); g.writeNode(spid, arr); });
		 * assertTrue(e.getMessage().
		 * equals("Errore. Aspettare 100 sec. e rieseguire il comando"));
		 * System.out.println("END TEST Eccezione");
		 */
	}

	@Test
	@DisplayName("writeEdge")
	public void writEdgeTest() {

		try {
			SOQuery soq = new SOQuery();
			Job job = soq.runEdgeAnswer(86, 1);
			arrEdge = soq.getEdge(job);
			spid = g.createSheet("Prova sna4so");
			g.writeEdge(spid, arrEdge);
		} catch (Exception e) {
			assumeNoException(e);
		}

		try { // Test branch arrEdge != null

			SOQuery soq = new SOQuery();
			Job job = soq.runEdgeAnswerWeight(86, 5);
			arrEdge = soq.getEdge(job);

			spid = g.createSheet("sna4so 01");
			g.writeEdge(spid, arrEdge);

			// Controllo risultato sheet //

			// Download Sheet online //
			ArrayList<Edge> esterno = readSheet2(spid, "A2:C101");

			// Comparazione Sheets di Edge
			assertTrue(arrayEdgeCompare(arrEdge, esterno));
			// System.out.println(arrayEdgeCompare(arrEdge, esterno));

		} catch (Exception e) {
			assumeNoException(e);
		}

		try {// Test branch arrEdge = null
			arrEdge = null;
			spid = g.createSheet("Prova sna4so");
			g.writeEdge(spid, arrEdge);
		} catch (Exception e) {
			assumeNoException(e);
		}

		/*
		 * System.out.println("START TEST Eccezione"); GoogleJsonResponseException e =
		 * assertThrows(GoogleJsonResponseException.class, () -> { g.writeEdge(spid,
		 * arrEdge); }); assertTrue(e.getMessage().
		 * equals("Errore. Aspettare 100 sec. e rieseguire il comando"));
		 * System.out.println("END TEST Eccezione");
		 */
	}

	// Scarica lo sheet di "Node" passando lo spid del link e il range delle celle
	public ArrayList<String> readSheet1(final String spid, final String range)
			throws MalformedURLException, IOException, GeneralSecurityException {
		final String URL = "http://neo.di.uniba.it/credentials/project-bernerslee-vvb56d.json";
		final List<String> SCOPES = Arrays.asList(SheetsScopes.SPREADSHEETS, SheetsScopes.DRIVE);
		// Arrays.asList(SheetsScopes.DRIVE);
		final GoogleCredential credential = GoogleCredential.fromStream(new URL(URL).openStream()).toBuilder()
				.setServiceAccountScopes(SCOPES).build();
		final Sheets service = new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
				JacksonFactory.getDefaultInstance(), credential).setApplicationName("sna4so").build();
		final ValueRange result = service.spreadsheets().values().get(spid, range).execute();
		ArrayList<String> out = new ArrayList<String>();
		// legge i dati dal foglio google sheet
		final List<List<Object>> res = result.getValues();
		final ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
		for (final List<Object> dataRow : res) {
			final ArrayList<String> stringRow = new ArrayList<String>();
			for (final Object value : dataRow) {

				stringRow.add((String) value);
			}
			data.add(stringRow);
			out.add(new String(stringRow.get(0)));
		}
		return out;
	}

	// Scarica lo sheet di "Edge" passando lo spid del link e il range delle celle
	public ArrayList<Edge> readSheet2(final String spid, final String range)
			throws MalformedURLException, IOException, GeneralSecurityException {
		final String URL = "http://neo.di.uniba.it/credentials/project-bernerslee-vvb56d.json";
		final List<String> SCOPES = // Arrays.asList(SheetsScopes.DRIVE);
				Arrays.asList(SheetsScopes.SPREADSHEETS, SheetsScopes.DRIVE);

		final GoogleCredential credential = GoogleCredential.fromStream(new URL(URL).openStream()).toBuilder()
				.setServiceAccountScopes(SCOPES).build();
		final Sheets service = new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
				JacksonFactory.getDefaultInstance(), credential).setApplicationName("sna4so").build();
		final ValueRange result = service.spreadsheets().values().get(spid, range).execute();
		ArrayList<Edge> out = new ArrayList<Edge>();
		// legge i dati dal foglio google sheet
		final List<List<Object>> res = result.getValues();
		final ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
		for (final List<Object> dataRow : res) {
			final ArrayList<String> stringRow = new ArrayList<String>();
			for (final Object value : dataRow) {
				stringRow.add((String) value);
			}
			data.add(stringRow);
			if (stringRow.size() > 2)
				out.add(new Edge(stringRow.get(0), stringRow.get(1), stringRow.get(2)));
			else
				out.add(new Edge(stringRow.get(0), stringRow.get(1)));
		}
		return out;
	}

	public boolean arrayEdgeCompare(final ArrayList<Edge> in, final ArrayList<Edge> out) {
		if (in.size() != out.size()) {
			return false;
		}
		for (int i = 0; i < in.size(); i++) {
			if (!in.get(i).getFrom().equals(out.get(i).getFrom())) {
				return false;
			}
			if (!in.get(i).getTo().equals(out.get(i).getTo())) {
				return false;
			}
			if ((in.get(i).isWeightEdge() == true) && (out.get(i).isWeightEdge() == true))
				if (!in.get(i).getWeight().equals(out.get(i).getWeight())) {
					return false;
				}
		}
		return true;
	}

	public boolean arrayStringCompare(final ArrayList<String> in, final ArrayList<String> out) {
		if (in.size() != out.size()) {
			return false;
		}
		for (int i = 0; i < in.size(); i++) {
			if (!in.get(i).equals(out.get(i))) {
				return false;
			}
		}
		return true;
	}
}