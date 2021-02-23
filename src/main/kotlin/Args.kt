import com.xenomachina.argparser.ArgParser

/**
 * Command-line arguments description
 */
class Args(parser: ArgParser) {
    val h by parser.flagging("The size should be displayed in a human-readable format: depending on the file size, the result is displayed in bytes, kilobytes, megabytes or gigabytes and is supplemented with the appropriate unit of measurement (B, KB, MB, GB). If this flag is not specified, the size should be printed in kilobytes and without a unit.")
    val c by parser.flagging("For all files passed to the input, you need to display the total size.")
    val si by parser.flagging("--si", help = "For all units used, the base should be 1000 (SI) instead 1024 (IEC)")
    val files by parser.positionalList("The name of the file whose size you want to output. There can be several such parameters.")
}