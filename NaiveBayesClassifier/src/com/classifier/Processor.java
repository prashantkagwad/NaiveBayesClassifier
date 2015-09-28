package com.classifier;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.StringTokenizer;

public class Processor {

	private ArrayList<String> negArrayList = new ArrayList<String>();
	private ArrayList<String> posArrayList = new ArrayList<String>();

	// private HashMap<String, WordDetails> wordMap = new HashMap<String,
	// WordDetails>();
	private HashMap<String, String> wordMap = new HashMap<String, String>();

	private HashMap<String, Integer> negWordCount = new HashMap<String, Integer>();
	private HashMap<String, Integer> posWordCount = new HashMap<String, Integer>();

	private HashMap<String, Double> negWordProb = new HashMap<String, Double>();
	private HashMap<String, Double> posWordProb = new HashMap<String, Double>();

	private int totalNumberOfPOSWords = 0;
	private int totalNumberOfNEGWords = 0;

	private int distinctPOSWords = 0;
	private int distinctNEGWords = 0;

	public Processor() {
		// TODO Auto-generated constructor stub
	}

	public ArrayList<String> getNegArrayList() {
		return negArrayList;
	}

	public void setNegArrayList(ArrayList<String> negArrayList) {
		this.negArrayList = negArrayList;
	}

	public ArrayList<String> getPosArrayList() {
		return posArrayList;
	}

	public void setPosArrayList(ArrayList<String> posArrayList) {
		this.posArrayList = posArrayList;
	}

	public HashMap<String, Integer> getNegWordCount() {
		return negWordCount;
	}

	public void setNegWordCount(HashMap<String, Integer> negWordCount) {
		this.negWordCount = negWordCount;
	}

	public HashMap<String, Integer> getPosWordCount() {
		return posWordCount;
	}

	public void setPosWordCount(HashMap<String, Integer> posWordCount) {
		this.posWordCount = posWordCount;
	}

	public HashMap<String, Double> getNegWordProb() {
		return negWordProb;
	}

	public void setNegWordProb(HashMap<String, Double> negWordProb) {
		this.negWordProb = negWordProb;
	}

	public HashMap<String, Double> getPosWordProb() {
		return posWordProb;
	}

	public void setPosWordProb(HashMap<String, Double> posWordProb) {
		this.posWordProb = posWordProb;
	}

	public int getTotalNumberOfPOSWords() {
		return totalNumberOfPOSWords;
	}

	public void setTotalNumberOfPOSWords(int totalNumberOfPOSWords) {
		this.totalNumberOfPOSWords = totalNumberOfPOSWords;
	}

	public int getTotalNumberOfNEGWords() {
		return totalNumberOfNEGWords;
	}

	public void setTotalNumberOfNEGWords(int totalNumberOfNEGWords) {
		this.totalNumberOfNEGWords = totalNumberOfNEGWords;
	}

	public int getDistinctPOSWords() {
		return distinctPOSWords;
	}

	public void setDistinctPOSWords(int distinctPOSWords) {
		this.distinctPOSWords = distinctPOSWords;
	}

	public int getDistinctNEGWords() {
		return distinctNEGWords;
	}

	public void setDistinctNEGWords(int distinctNEGWords) {
		this.distinctNEGWords = distinctNEGWords;
	}

	public void readNEGFolder(String folderPath) {

		// txt_sentoken
		File folder = new File(folderPath);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {

			if (listOfFiles[i].isFile()) {

				// System.out.println("File " + listOfFiles[i].getName());
				negArrayList.add(listOfFiles[i].getName());

			} else if (listOfFiles[i].isDirectory()) {

				System.out.println("Directory " + listOfFiles[i].getName());
			}
		}
	}

	public void negTrain(int start, int end) {

		for (int i = start; i < end; i++) {

			String negFilePath = System.getProperty("user.dir")
					+ "\\txt_sentoken\\neg\\" + negArrayList.get(i);
			readNEGFile(negFilePath);
		}
	}

	public void readNEGFile(String fileName) {

		FileReader file = null;
		BufferedReader reader = null;
		String line = "";

		try {
			file = new FileReader(fileName);
			reader = new BufferedReader(file);
			while ((line = reader.readLine()) != null) {

				StringTokenizer st = new StringTokenizer(line);

				while (st.hasMoreTokens()) {

					String word = st.nextToken().toLowerCase();
					// String word = st[iterator];

					if (negWordCount.containsKey(word)) {

						negWordCount.put(word, negWordCount.get(word) + 1);

					} else {

						negWordCount.put(word, 1);
						distinctNEGWords++;
					}

					if (!wordMap.containsKey(word)) {
						wordMap.put(word, "");
					}

					totalNumberOfNEGWords++;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (file != null) {
				try {
					reader.close();
					file.close();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void readPOSFolder(String folderPath) {

		// txt_sentoken
		File folder = new File(folderPath);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {

			if (listOfFiles[i].isFile()) {

				// System.out.println("File " + listOfFiles[i].getName());
				posArrayList.add(listOfFiles[i].getName());

			} else if (listOfFiles[i].isDirectory()) {

				System.out.println("Directory " + listOfFiles[i].getName());
			}
		}
	}

	public void posTrain(int start, int end) {

		for (int i = start; i < end; i++) {

			String posFilePath = System.getProperty("user.dir")
					+ "\\txt_sentoken\\pos\\" + posArrayList.get(i);

			readPOSFile(posFilePath);
		}
	}

	public void readPOSFile(String fileName) {

		FileReader file = null;
		BufferedReader reader = null;
		String line = "";

		try {
			file = new FileReader(fileName);
			reader = new BufferedReader(file);
			while ((line = reader.readLine()) != null) {

				StringTokenizer st = new StringTokenizer(line);

				while (st.hasMoreTokens()) {

					String word = st.nextToken().toLowerCase();
					// String word = st[iterator];

					if (posWordCount.containsKey(word)) {

						posWordCount.put(word, posWordCount.get(word) + 1);

					} else {

						posWordCount.put(word, 1);
						distinctPOSWords++;
					}

					if (!wordMap.containsKey(word)) {
						wordMap.put(word, "");
					}

					totalNumberOfPOSWords++;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (file != null) {
				try {
					reader.close();
					file.close();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void computeProb() {

		for (Entry<String, Integer> entry : negWordCount.entrySet()) {

			String word = entry.getKey().toLowerCase();
			Integer negCount = entry.getValue();

			double negProb = 0.0;
			if (negCount > 0)
				negProb = (double) negCount / (double) totalNumberOfNEGWords;
			else
				negProb = (double) 1.0
						/ (double) (totalNumberOfNEGWords + distinctNEGWords);

			negProb = (double) Math.log10(negProb) / (double) Math.log10(2);

			negWordProb.put(word, negProb);
		}

		for (Entry<String, Integer> entry : posWordCount.entrySet()) {

			String word = entry.getKey().toLowerCase();
			Integer posCount = entry.getValue();

			double posProb = 0.0;
			if (posCount > 0)
				posProb = (double) posCount / (double) totalNumberOfPOSWords;
			else
				posProb = (double) 1.0
						/ (double) (totalNumberOfPOSWords + distinctPOSWords);

			posProb = (double) Math.log10(posProb) / (double) Math.log10(2);

			posWordProb.put(word, posProb);
		}
	}

	public void genrateModelFile(String fileName) {

		File file = new File(fileName);
		FileWriter fileWriter = null;
		BufferedWriter writer = null;

		try {

			fileWriter = new FileWriter(fileName);
			writer = new BufferedWriter(fileWriter);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			for (Entry<String, String> entry : wordMap.entrySet()) {

				String word = entry.getKey();

				double negProb = 0.0;
				if (negWordCount.containsKey(word)) {

					int negCount = negWordCount.get(word);
					negProb = (double) negCount
							/ (double) totalNumberOfNEGWords;
				} else {

					negProb = (double) 1.0
							/ (double) (totalNumberOfNEGWords + distinctNEGWords);
				}
				negProb = (double) Math.log10(negProb) / (double) Math.log10(2);

				double posProb = 0.0;
				if (posWordCount.containsKey(word)) {

					int posCount = posWordCount.get(word);
					posProb = (double) posCount
							/ (double) totalNumberOfPOSWords;
				} else {

					posProb = (double) 1.0
							/ (double) (totalNumberOfPOSWords + distinctPOSWords);
				}

				posProb = (double) Math.log10(posProb) / (double) Math.log10(2);

				writer.write(word + " " + posProb + " " + negProb + "\n");
			}
			
			posWordCount.clear();
			posWordProb.clear();
			negWordCount.clear();
			negWordCount.clear();
			wordMap.clear();
			
			writer.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			if (fileWriter != null) {
				try {
					writer.close();
					fileWriter.close();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void printNEGFiles() {
		System.out.println("Number of neg files : " + negArrayList.size());

		for (int i = 0; i < negArrayList.size(); i++) {
			// System.out.println(negArrayList.get(i));
		}
	}

	public void printPOSFiles() {
		System.out.println("Number of pos files : " + posArrayList.size());

		for (int i = 0; i < posArrayList.size(); i++) {
			// System.out.println(posArrayList.get(i));
		}
	}

	public void printReport() {

		System.out.println("Number of pos files : " + posArrayList.size());
		System.out.println("Number of neg files : " + negArrayList.size());
		System.out.println("wordMap size : " + wordMap.size());

		System.out.println("totalNumberOfPOSWords : " + totalNumberOfPOSWords);
		System.out.println("totalNumberOfNEGWords : " + totalNumberOfNEGWords);
		System.out.println("distinctPOSWords : " + distinctPOSWords);
		System.out.println("distinctNEGWords : " + distinctNEGWords);

	}
}
