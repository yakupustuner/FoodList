package com.learn.foodlist.presentation.details


import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberAsyncImagePainter
import com.learn.foodlist.R
import com.learn.foodlist.presentation.components.InfoBox
import com.learn.foodlist.presentation.home.HomeViewModel
import com.learn.foodlist.ui.theme.*
import com.learn.foodlist.util.Constants
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun DetailsScreen(
    navController: NavHostController,
    recipeId:Int,image:String,title:String,
    summary:String,readyInMinutes:Int,
    aggregateLikes:Int
) {



    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Expanded)
    )
    val currentSheetFraction = scaffoldState.currentSheetFraction

    val radiusAnim by animateDpAsState(
        targetValue =
        if (currentSheetFraction == 1f)
            EXTRA_LARGE_PADDING
        else
            EXPANDED_RADIUS_LEVEL
    )
    BottomSheetScaffold(
        sheetShape = RoundedCornerShape(
            topStart = radiusAnim,
            topEnd = radiusAnim
        ),
        scaffoldState = scaffoldState,
        sheetPeekHeight = MIN_SHEET_HEIGHT,
        sheetContent = {
            BottomSheetContent(
                infoBoxIconColor = MaterialTheme.colors.primary,
                sheetBackgroundColor = MaterialTheme.colors.surface,
                contentColor = MaterialTheme.colors.titleColor,
                title = title,
                summary = summary,
                readyInMinutes = readyInMinutes,
                aggregateLikes = aggregateLikes
            )
        },
        content = {
            BackgroundContent(
                image = image,
                imageFraction = currentSheetFraction,
                backgroundColor = MaterialTheme.colors.surface,
                onCloseClicked = {
                    navController.popBackStack()
                }
            )
        }
    )
}

@Composable
fun BottomSheetContent(
    homeViewModel: HomeViewModel = hiltViewModel(),
    title:String,
    summary:String,readyInMinutes:Int,
    aggregateLikes:Int,
    infoBoxIconColor: Color = MaterialTheme.colors.primary,
    sheetBackgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color =  MaterialTheme.colors.titleColor
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val scrollToPosition  by remember { mutableStateOf(0F) }
    Column(
        modifier = Modifier
            .background(sheetBackgroundColor)
            .padding(all = LARGE_PADDING)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = LARGE_PADDING),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .weight(8f),
                text = title,
                color = contentColor,
                fontSize = MaterialTheme.typography.h4.fontSize,
                fontWeight = FontWeight.Bold
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MEDIUM_PADDING),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            InfoBox(
                icon = painterResource(id = R.drawable.ic_heart),
                iconColor = infoBoxIconColor,
                titleText = "$aggregateLikes",
                smallText = stringResource(R.string.like),
                textColor = contentColor
            )
            InfoBox(
                icon = painterResource(id = R.drawable.ic_clock),
                iconColor = infoBoxIconColor,
                titleText = "$readyInMinutes",
                smallText = stringResource(R.string.minute),
                textColor = contentColor
            )
        }
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.recipe),
            color = contentColor,
            fontSize = MaterialTheme.typography.subtitle1.fontSize,
            fontWeight = FontWeight.Bold
        )
        Column(Modifier.verticalScroll(scrollState)) {
            Text(
                homeViewModel.parseHtml(summary),
                modifier = Modifier.clickable {
                    coroutineScope.launch {
                        scrollState.animateScrollTo(scrollToPosition.roundToInt())
                    }
                }
                    .alpha(ContentAlpha.medium)
                    .padding(bottom = MEDIUM_PADDING),
                color = contentColor,
                fontSize = MaterialTheme.typography.body1.fontSize,
            )
        }

    }
}

@Composable
fun BackgroundContent(
    imageFraction: Float = 1f,
    backgroundColor: Color = MaterialTheme.colors.surface,
    onCloseClicked: () -> Unit,
    image: String
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Image(painter = rememberAsyncImagePainter(image),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = imageFraction + Constants.MIN_BACKGROUND_IMAGE_HEIGHT)
                .align(Alignment.TopStart),
            contentDescription = stringResource(id = R.string.recipe_image),
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                modifier = Modifier.padding(all = SMALL_PADDING),
                onClick = { onCloseClicked() }
            ) {
                Icon(
                    modifier = Modifier.size(INFO_ICON_SIZE),
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.close_icon),
                    tint = Color.Black
                )
            }
        }
    }
}

@ExperimentalMaterialApi
val BottomSheetScaffoldState.currentSheetFraction: Float
    get() {
        val fraction = bottomSheetState.progress.fraction
        val targetValue = bottomSheetState.targetValue
        val currentValue = bottomSheetState.currentValue

        return when {
            currentValue == BottomSheetValue.Collapsed && targetValue == BottomSheetValue.Collapsed -> 1f
            currentValue == BottomSheetValue.Expanded && targetValue == BottomSheetValue.Expanded -> 0f
            currentValue == BottomSheetValue.Collapsed && targetValue == BottomSheetValue.Expanded -> 1f - fraction
            currentValue == BottomSheetValue.Expanded && targetValue == BottomSheetValue.Collapsed -> 0f + fraction
            else -> fraction
        }
    }