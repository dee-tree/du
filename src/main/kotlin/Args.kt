import com.xenomachina.argparser.ArgParser

class Args(parser: ArgParser) {
    val h by parser.flagging("human readable")
    val c by parser.flagging("all as one")
    val si by parser.flagging("--si", help = "base = 1000")
    val files by parser.positionalList("files")
}