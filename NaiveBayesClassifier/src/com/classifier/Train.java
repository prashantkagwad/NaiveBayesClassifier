package com.classifier;

public class Train {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {

			// Check for number of arguments.
			if (args.length != 4) {

				System.err.println("You must call Train as "
						+ "follows:\n\njava Train "
						+ "-train <Training_Folder> -cfv <Cross_Fold>\n");
				System.exit(1);
			}

			// int cfv = 10;

			String workingDir = System.getProperty("user.dir");

			// Read in the file names.
			String negFolderName = "neg";
			String posFolderName = "pos";
			String lmPath = System.getProperty("user.dir");

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

			Processor processor = new Processor();

			processor.readNEGFolder(negfolderPath);
			processor.readPOSFolder(posfolderPath);

			int numberOfNEGFiles = processor.getNegArrayList().size();
			int numberOfPOSFiles = processor.getPosArrayList().size();

			int numberOfNEGFilesPerBlock = numberOfNEGFiles / cfv;
			int numberOfPOSFilesPerBlock = numberOfPOSFiles / cfv;

			numberOfNEGFilesPerBlock = 100;
			numberOfPOSFilesPerBlock = 100;

			for (int itr = 0; itr < cfv; itr++) {

				//
				processor.negTrain(0, (itr * numberOfNEGFilesPerBlock) - 1);
				processor
						.negTrain(
								((itr * numberOfNEGFilesPerBlock) + numberOfNEGFilesPerBlock),
								numberOfNEGFiles);
				// System.out.println("Train(0, "
				// + ((i * numberOfNEGFilesPerBlock) - 1) + ")");
				// System.out.println();
				// System.out
				// .println("Train("
				// + ((i * numberOfPOSFilesPerBlock) + numberOfPOSFilesPerBlock)
				// + ", " + numberOfPOSFiles + ")");
				// System.out.println();
				// System.out.println();

				processor.posTrain(0, (itr * numberOfPOSFilesPerBlock) - 1);
				processor
						.posTrain(
								((itr * numberOfPOSFilesPerBlock) + numberOfPOSFilesPerBlock),
								numberOfPOSFiles);

				//
				// processor.computeProb();

				String lmFileName = "model" + itr + ".txt";

				lmPath = System.getProperty("user.dir") + "\\" + lmFileName;

				processor.genrateModelFile(lmPath);
			}

			// processor.negTrain(0, 899);
			// processor.negTrain(1000, 1000);
			// processor.posTrain(0, 899);
			// processor.negTrain(1000, 1000);
			//
			// processor.computeProb();
			//
			// String lmFileName = "model9" + ".txt";
			//
			// lmPath = System.getProperty("user.dir") + "\\" + lmFileName;
			// processor.genrateModelFile(lmPath);

			// processor.printNEGFiles();
			// processor.printPOSFiles();

			processor.printReport();

			long elapsedTime = System.currentTimeMillis() - start;
			System.out.println("Model files generated in " + elapsedTime
					+ " milli-seconds at " + workingDir);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
