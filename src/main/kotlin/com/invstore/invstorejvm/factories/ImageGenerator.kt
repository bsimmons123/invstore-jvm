package com.invstore.invstorejvm.factories

import java.awt.Color
import java.awt.Font
import java.awt.GradientPaint
import java.awt.geom.Ellipse2D
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO

class ImageGenerator {
    @Throws(IOException::class)
    fun generateImage(id: String, firstName: String, lastName: String, path: Path, fileSuffix: String) {
        val img = BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB)
        val g2d = img.createGraphics()

        // Implement color gradient
        val paint = GradientPaint(0f, 0f, Color.MAGENTA, 0f, img.height.toFloat(), Color.CYAN)
        g2d.paint = paint

        // Create circular shape avatar
        g2d.fill(Ellipse2D.Double(0.0, 0.0, img.width.toDouble(), img.height.toDouble()))

        g2d.font = Font("SansSerif", Font.BOLD, 40)

        // Set color for the text on top
        g2d.color = Color.WHITE

        val firstNameInitial = firstName[0].uppercase()
        val lastNameInitial = lastName[0].uppercase()

        // Getting dimensions needed to center the initials
        val fm = g2d.fontMetrics
        val stringWidth = fm.stringWidth("$firstNameInitial$lastNameInitial")
        val stringHeight = fm.ascent - fm.descent

        // Centering text in the middle of circle
        val widthX = (img.width - stringWidth) / 2
        val heightY = (img.height + stringHeight) / 2

        // Setting the initials to a graphic
        g2d.drawString("$firstNameInitial$lastNameInitial", widthX, heightY)

        g2d.dispose()

        val directory = File("images/${path.value}")
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