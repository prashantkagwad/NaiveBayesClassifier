package com.classifier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Evaluator {

	private ArrayList<String> negArrayList = new ArrayList<String>();
	private ArrayList<String> posArrayList = new ArrayList<String>();

	private HashMap<String, Double> posWordProbs = new HashMap<String, Double>();
	private HashMap<String, Double> negWordProbs = new HashMap<String, Double>();

	private int numberOfNEGClassifiedCorrectly = 0;
	private int numberOfPOSClassifiedCorrectly = 0;

	public Evaluator() {
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

	public HashMap<String, Double> getPosWordProbs() {
		return posWordProbs;
	}

	public void setPosWordProbs(HashMap<String, Double> posWordProbs) {
		this.posWordProbs = posWordProbs;
	}

	public HashMap<String, Double> getNegWordProbs() {
		return negWordProbs;
	}

	public void setNegWordProbs(HashMap<String, Double> negWordProbs) {
		this.negWordProbs = negWordProbs;
	}

	public int getNumberOfNEGClassifiedCorrectly() {
		return numberOfNEGClassifiedCorrectly;
	}

	public void setNumberOfNEGClassifiedCorrectly(
			int numberOfNEGClassifiedCorrectly) {
		this.numberOfNEGClassifiedCorrectly = numberOfNEGClassifiedCorrectly;
	}

	public int getNumberOfPOSClassifiedCorrectly() {
		return numberOfPOSClassifiedCorrectly;
	}

	public void setNumberOfPOSClassifiedCorrectly(
			int numberOfPOSClassifiedCorrectly) {
		this.numberOfPOSClassifiedCorrectly = numberOfPOSClassifiedCorrectly;
	}

	public void readModelFile(String fileName) {

		FileReader file = null;
		BufferedReader reader = null;
		String line = "";

		try {
			file = new FileReader(fileName);
			reader = new BufferedReader(file);
			while ((line = reader.readLine()) != null) {

				String st[] = line.split(" ");

				if (st.length == 3) {

					String word = st[0].toLowerCase();
					// String word = st[0];
					Double negProb = Double.parseDouble(st[2]);
					Double posProb = Double.parseDouble(st[1]);

					negWordProbs.put(word, negProb);
					posWordProbs.put(word, posProb);

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

	public void negTest(int start, int end) {

		for (int i = start; i <= end; i++) {

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

			double negProb = 0.0;
			double posProb = 0.0;

			file = new FileReader(fileName);
			reader = new BufferedReader(file);
			while ((line = reader.readLine()) != null) {

				String st[] = line.split(" ");

				for (int iterator = 0; iterator < st.length; iterator++) {

					String word = st[iterator].toLowerCase();

					if (negWordProbs.containsKey(word))
						negProb = negProb + negWordProbs.get(word);

					if (posWordProbs.containsKey(word))
						posProb = posProb + posWordProbs.get(word);

				}
			}

			// System.out.println("NEG : For file : " + fileName);
			// System.out.println("NegProb : " + negProb + "  PosProb : "
			// + posProb);

			if (negProb > posProb) {
				numberOfNEGClassifiedCorrectly++;
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

	public void posTest(int start, int end) {

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

			double negProb = 0.0;
			double posProb = 0.0;

			file = new FileReader(fileName);
			reader = new BufferedReader(file);
			while ((line = reader.readLine()) != null) {

				String st[] = line.split(" ");

				for (int iterator = 0; iterator < st.length; iterator++) {

					String word = st[iterator].toLowerCase();

					if (negWordProbs.containsKey(word))
						negProb = negProb + negWordProbs.get(word);

					if (posWordProbs.containsKey(word))
						posProb = posProb + posWordProbs.get(word);
				}
			}

			// System.out.println("POS : For file : " + fileName);
			// System.out.println("NegProb : " + negProb + "  PosProb : "
			// + posProb);

			if (negProb < posProb) {
				numberOfPOSClassifiedCorrectly++;
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

	public void clear() {

		negWordProbs.clear();
		posWordProbs.clear();
	}

	public void print() {

		double negAvg = numberOfNEGClassifiedCorrectly / (double) 100;
		double posAvg = numberOfPOSClassifiedCorrectly / (double) 100;
		double avg = (negAvg + posAvg) / (double) 2;

		System.out.println("Average Accuracy : " + avg);
	}
}
