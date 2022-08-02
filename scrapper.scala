import org.jsoup.Jsoup
import org.jsoup.nodes.{Document, Element}
import org.jsoup.select.Elements

import scala.collection.JavaConverters._
import scala.collection.mutable

object scrapper {
    def tableFinder(url: String): Element = {
        Jsoup.connect(url).get().getElementsByClass("engineTable").first()
    }

    def formRows(foundTable: Element): Elements = {
        foundTable.getElementsByTag("td")
    }

    def formColumnName(foundTable: Element): Elements = {
        foundTable.getElementsByTag("th")
    }

    def strConv(formedColOrRows: Elements): mutable.Seq[String] = {
        formedColOrRows.asScala.map(ele => ele.text())
    }

    def main(args: Array[String]) {
        val url = "https://stats.espncricinfo.com/ci/engine/records/team/match_results.html?id=14452;type=tournament"
        val table = tableFinder(url)
        val tableData = formRows(table)
        val scalaConv = strConv(tableData)

        val columnCount = strConv(formColumnName(table)).length // 74

        val groupedAsPerFileFormat = scalaConv.grouped(columnCount).toArray
        val rowMake = groupedAsPerFileFormat.map(x => x.toString().replace("ArrayBuffer(", "").replace(")", ""))
        //        reflect.io.File("C://Users/tanvi/OneDrive/Desktop/Sample.csv").appendAll(x + "\n")
    }
}
