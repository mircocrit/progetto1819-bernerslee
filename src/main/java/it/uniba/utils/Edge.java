package it.uniba.utils;
/**
 * Edge.java ||Entity||
 * This class includes getter and setter methods for each from/to/weight parameters of the queries
 * It includes a method which discovers if the Edge object has weight and 2 constructor methods (wit or without weight)
 */
public class Edge {
	private static boolean hasWeight = false;
	private String from;
	private String to;
	private String weight;
	/**
	 * Metodo statico che permette di scoprire se gli oggetti utilizzati hanno peso
	 * oppure no
	 * 
	 * @return true is edge has weight
	 */
	public static boolean isWeightEdge() {
		return isHasWeight();
	}

	/**
	 * Costruttore di Edge con due parametri
	 * 
	 * @param pFrom from node
	 * @param pTo to node
	 */
	public Edge(final String pFrom, final String pTo) {
		this.from = pFrom;
		this.to = pTo;
	}

	/**
	 * Costruttore di Edge con tre parametri
	 * 
	 * @param pFrom from node
	 * @param pTo to node
	 * @param pWeight weight of edge
	 */
	public Edge(final String pFrom, final String pTo, final String pWeight) {
		this.from = pFrom;
		this.to = pTo;
		this.weight = pWeight;
		Edge.setHasWeight(true);
	}

	/**
	 * Getter per from
	 * 
	 * @return il valore del membro 'from'
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * Setter per from
	 * 
	 * @param  pFrom from node
	 */
	public void setFrom(final String pFrom) {
		this.from = pFrom;
	}

	/**
	 * Getter per to
	 * 
	 * @return il valore del membro 'to'
	 */
	public String getTo() {
		return to;
	}

	/**
	 * Setter per to
	 * 
	 * @param pTo to node
	 */
	public void setTo(final String pTo) {
		this.to = pTo;
	}

	/**
	 * Getter per weight
	 * 
	 * @return il valore del membro 'weight'
	 */
	public String getWeight() {
		return weight;
	}

	/**
	 * Setter per weight
	 * 
	 * @param pWeight  weight of edge
	 */
	public void setWeight(final String pWeight) {
		this.weight = pWeight;
	}

	/**
	 * Controlla la presenza o meno del peso su un arco
	 * 
	 * @return true se weight non è vuoto, false altrimenti
	 */
	public boolean emptyWeight() {
		return this.weight.isEmpty();
	}

	/**
	 * 
	 * @return true if edge has weight
	 */
	public static boolean isHasWeight() {
		return hasWeight;
	}

	/**
	 * 
	 * @param weight of edge
	 */
	public static void setHasWeight(final boolean weight) {
		Edge.hasWeight = weight;
	}

}
