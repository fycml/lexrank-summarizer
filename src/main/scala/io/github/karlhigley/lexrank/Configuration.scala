package io.github.karlhigley.lexrank

class Configuration(args: Array[String]) {
  var inputPath     = "input"
  var outputPath    = "output"
  var stopwordsPath = "stopwords"

  var length        = 5
  var cutoff        = 0.8
  var threshold     = 0.1
  var convergence   = 0.001

  parse(args.toList)

  private def parse(args: List[String]): Unit = args match {
    case ("--input" | "-i") :: path :: tail =>
      inputPath = path
      parse(tail)

    case ("--output" | "-o") :: path :: tail =>
      outputPath = path
      parse(tail)

    case ("--stopwords" | "-s") :: path :: tail =>
      stopwordsPath = path
      parse(tail)

    case ("--length" | "-l") :: value :: tail =>
      length = value.toInt
      parse(tail)

    case ("--boilerplate" | "-b") :: value :: tail =>
      cutoff = value.toDouble
      parse(tail)

    case ("--threshold" | "-t") :: value :: tail =>
      threshold = value.toDouble
      parse(tail)

    case ("--convergence" | "-c") :: value :: tail =>
      convergence = value.toDouble
      parse(tail)

    case ("--help" | "-h") :: tail =>
      printUsageAndExit(0)

    case _ =>
  }

  /**
   * Print usage and exit JVM with the given exit code.
   */
  private def printUsageAndExit(exitCode: Int) {
    val usage =
     s"""
      |Usage: spark-submit --class io.github.karlhigley.Summarizer <jar-path> [summarizer options]
      |
      |Options:
      |   -i PATH, --input PATH          Relative path of input files (default: "./input")
      |   -o PATH, --output PATH         Relative path of output files (default: "./output")
      |   -s PATH, --stopwords PATH      Relative path of stopwords file (default: "./stopwords")
      |   -l VALUE, --length VALUE       Number of sentences to extract from each document (default: 5) 
      |   -b VALUE, --boilerplate VALUE  Similarity cutoff for cross-document boilerplate filtering (default: 0.8)
      |   -t VALUE, --threshold VALUE    Similarity threshold for LexRank graph construction (default: 0.1)
      |   -c VALUE, --convergence VALUE  Convergence tolerance for PageRank graph computation (default: 0.001)
     """.stripMargin
    System.err.println(usage)
    System.exit(exitCode)
  }

}