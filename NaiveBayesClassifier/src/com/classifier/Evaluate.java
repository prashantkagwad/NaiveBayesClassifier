package com.classifier;

public class Evaluate {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {

			// Check for number of arguments.
			if (args.length != 4) {

				System.err.println("You must call Evaluate as "
						+ "follows:\n\njava Evaluate "
						+ "-test <Test_Folder> -cfv <Cross_Fold>\n");
				System.exit(1);
			}

			// int cfv = 10;

			String workingDir = System.getProperty("user.dir");

			// Read in the file names.
			String negFolderName = "neg";
			String posFolderName = "pos";

			String negfolderPath = workingDir + "\\" + args[1] + "\\"
					+ negFolderName;
			String posfolderPath = workingDir + "\\" + args[1] + "\\"
					+ posFolderName;

			int cfv = Integer.parseInt(args[3]);
			// String trainingFileName = "hw3_train.txt";
			// String lmFileName = "model.txt";

			// String negfolderPath = System.getProperty("user.dir")
			// + "\\txt_sentoken\\" + negFolderName;
			// String posfolderPath = System.getProperty("user.dir")
			// + "\\txt_sentoken\\" + posFolderName;

			long start = System.currentTimeMillis();

			Evaluator eval = new Evaluator();

			eval.readNEGFolder(negfolderPath);
			eval.readPOSFolder(posfolderPath);

			int numberOfNEGFiles = eval.getNegArrayList().size();
			int numberOfPOSFiles = eval.getPosArrayList().size();

			int numberOfNEGFilesPerBlock = numberOfNEGFiles / cfv;
			int numberOfPOSFilesPerBlock = numberOfPOSFiles / cfv;

			double avgOfBlocks[] = new double[cfv];
			for (int itr = 0; itr < cfv; itr++) {

				String lmFileName = "model" + itr + ".txt";

				String lmPath = System.getProperty("user.dir") + "\\"
						+ lmFileName;

				eval.readModelFile(lmPath);

				//
				eval.negTest(
						(itr * numberOfNEGFilesPerBlock),
						((itr * numberOfNEGFilesPerBlock) + (numberOfNEGFilesPerBlock - 1)));

				//
				eval.posTest(
						(itr * numberOfPOSFilesPerBlock),
						((itr * numberOfPOSFilesPerBlock) + (numberOfPOSFilesPerBlock - 1)));

				//
				double avg = (eval.getNumberOfNEGClassifiedCorrectly() + eval
						.getNumberOfPOSClassifiedCorrectly())
						/ (double) (numberOfNEGFilesPerBlock + numberOfPOSFilesPerBlock);
				avgOfBlocks[itr] = avg;

				System.out
						.println("Testing("
								+ (itr * numberOfNEGFilesPerBlock)
								+ ", "
								+ ((itr * numberOfNEGFilesPerBlock) + (numberOfNEGFilesPerBlock - 1))
								+ ") \t-- using model file : "
								+ lmFileName
								+ " classified correctly : "
								+ (eval.getNumberOfNEGClassifiedCorrectly() + eval
										.getNumberOfPOSClassifiedCorrectly())
								+ "/"
								+ (numberOfNEGFilesPerBlock + numberOfPOSFilesPerBlock)
								+ " = " + avg);

				eval.setNumberOfNEGClassifiedCorrectly(0);
				eval.setNumberOfPOSClassifiedCorrectly(0);
				eval.clear();
			}

			double avg = 0.0;
			for (int itr = 0; itr < cfv; itr++) {

				System.out.print(avgOfBlocks[itr] + " ");
				avg = avg + avgOfBlocks[itr];
			}
			System.out.println();

			System.out.println("Average : " + (avg / cfv) * 100);
			System.out.println();

			long elapsedTime = System.currentTimeMillis() - start;
			System.out.println("Avg computed in " + elapsedTime
					+ " milli-seconds");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
