package com.invstore.invstorejvm.factories

import java.awt.Color
import java.awt.Font
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO

class ImageGenerator {
    @Throws(IOException::class)
    fun generateImage(id: String, firstName: String, lastName: String, path: Path, fileSuffix: String) {
        val img = BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB)
        val g = img.createGraphics()
        g.color = Color.YELLOW // random color
        g.fillRect(0, 0, img.width, img.height)
        g.color = Color.BLACK
        g.font = Font("SansSerif", Font.BOLD, 40)
        g.drawString(firstName.substring(0, 1) + lastName.substring(0, 1), 20, 50)
        g.dispose()

        val directory = File("images/"+path.value)
        if (!directory.exists()) {
            directory.mkdirs()
        }

        val outputfile = File("${directory}/$id-$fileSuffix.png")
        ImageIO.write(img, "png", outputfile)
    }
}

enum class Path(val value: String) {
    USER("user-images"),
    LIST("list-images"),
    ITEM("item-images")
}