package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.ModeComment
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material.icons.outlined.Repeat
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class PostUiModel(
    val id: Int,
    val message: String,
    val author: String,
    val avatarUrl: String,
    val likes: Int,
    val liked: Boolean,
    val date: String,
)

@Composable
fun PostComponent(
    post: PostUiModel,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = post.author.take(1).uppercase(),
                    style = MaterialTheme.typography.titleMedium,
                )
            }

            Column(modifier = Modifier.weight(1f)) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = post.author,
                        style = MaterialTheme.typography.titleMedium,
                    )

                    Text(
                        text = " @${
                            post.author.lowercase().replace(" ", "")
                        } · ${formatDate(post.date)}",
                        style = MaterialTheme.typography.bodyMedium,
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Icon(
                        imageVector = Icons.Outlined.MoreHoriz,
                        contentDescription = "Más opciones",
                        modifier = Modifier.size(20.dp),
                    )
                }

                Text(
                    text = post.message,
                    modifier = Modifier.padding(top = 4.dp),
                    style = MaterialTheme.typography.bodyLarge,
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    PostAction(
                        icon = Icons.Outlined.ModeComment,
                        text = "0",
                    )

                    PostAction(
                        icon = Icons.Outlined.Repeat,
                        text = "0",
                    )

                    PostAction(
                        icon = Icons.Outlined.FavoriteBorder,
                        text = post.likes.toString(),
                    )
                }
            }
        }

        HorizontalDivider()
    }
}

@Composable
private fun PostAction(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(18.dp),
        )

        Text(
            text = text,
            modifier = Modifier.padding(start = 4.dp),
            style = MaterialTheme.typography.bodySmall,
        )
    }
}

private fun formatDate(date: String): String {
    return try {
        val parts = date.take(10).split("-")
        "${parts[2]}/${parts[1]}/${parts[0]}"
    } catch (_: Exception) {
        date
    }
}

@Preview(showBackground = true)
@Composable
fun PostComponentPreview() {
    PostComponent(
        post = PostUiModel(
            id = 1,
            message = "Hola, esta es una prueba del tweet",
            author = "Paloma Aguirre",
            avatarUrl = "",
            likes = 15,
            liked = false,
            date = "2026-06-20",
        ),
    )
}