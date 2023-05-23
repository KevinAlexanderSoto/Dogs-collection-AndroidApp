package com.kalex.dogescollection.core.values

import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val black = Color(0xffff000000)
val white = Color(0xffffffff)

val Primary = Color(0xff48680d)
val OnPrimary = Color(0xffffffff)
val PrimaryContainer = Color(0xffc9f088)
val OnPrimaryContainer = Color(0xff121f00)

val Secondary = Color(0xff396661)
val OnSecondary = Color(0xffffffff)
val SecondaryContainer = Color(0xffbcece6)
val OnSecondaryContainer = Color(0xff00201d)

val Tertiary = Color(0xff006c4e)
val OnTertiary = Color(0xffffffff)
val TertiaryContainer = Color(0xff86f8ca)
val OnTertiaryContainer = Color(0xff002115)

val Surface = Color(0xfffdfcff)
val OnSurface = Color(0xff1a1c1e)
val SurfaceVariant = Color(0xffdfe2eb)
val OnSurfaceVariant = Color(0xff43474e)

val RedSalsa = Color(0xffF05D5E)
val ColorOutline = Color(0xff73777f)
val ColorOnBackground = Color(0xff1a1c1e)

val LightColors = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryContainer,
    onPrimaryContainer = OnPrimaryContainer,
    secondary = Secondary,
    onSecondary = OnSecondary,
    secondaryContainer = SecondaryContainer,
    onSecondaryContainer = OnSecondaryContainer,
    tertiary = Tertiary,
    onTertiary = OnTertiary,
    tertiaryContainer = TertiaryContainer,
    onTertiaryContainer = OnTertiaryContainer,
    onBackground = ColorOnBackground,
    surface = Surface,
    onSurface = OnSurface,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = OnSurfaceVariant,
    error = RedSalsa,
    onError = RedSalsa,
    errorContainer = RedSalsa,
    onErrorContainer = RedSalsa,
    outline = ColorOutline,
    outlineVariant = ColorOutline,
)
