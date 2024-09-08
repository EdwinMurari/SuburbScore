import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.edwin.suburbscore.screen.FiltersSection
import com.edwin.suburbscore.screen.PostList
import com.edwin.suburbscore.screen.PostListUiState
import com.edwin.suburbscore.screen.PostListViewModel

@Composable
fun PostListScreen(
    viewModel: PostListViewModel = viewModel { PostListViewModel() },
) {
    val uiState by viewModel.uiState.collectAsState()

    PostListUi(
        uiState = uiState,
        onCategorySelect = viewModel::onCategorySelect,
        onSuburbSelect = viewModel::onSuburbSelect
    )
}

@Composable
fun PostListUi(
    uiState: PostListUiState,
    onCategorySelect: (String) -> Unit,
    onSuburbSelect: (String) -> Unit
) {
    AnimatedContent(uiState) { targetState ->
        when (targetState) {
            PostListUiState.Error -> {
                Text(text = "Something went wrong", modifier = Modifier.padding(16.dp))
            }

            PostListUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is PostListUiState.Success -> {
                PostListSuccess(
                    uiState = targetState,
                    onCategorySelect = onCategorySelect,
                    onSuburbSelect = onSuburbSelect
                )
            }
        }
    }
}

@Composable
fun PostListSuccess(
    uiState: PostListUiState.Success,
    onCategorySelect: (String) -> Unit,
    onSuburbSelect: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Victoria Suburb Posts") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.Create,
                    contentDescription = "Create"
                )
            }
        }
    ) { innerPadding ->
        Row(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            FiltersSection(
                modifier = Modifier.width(250.dp),
                categories = uiState.categories,
                suburbs = uiState.suburbs,
                selectedCategories = uiState.selectedCategories,
                selectedSuburb = uiState.selectedSuburb,
                onCategorySelect = onCategorySelect,
                onSuburbSelect = onSuburbSelect
            )

            Spacer(modifier = Modifier.width(16.dp))

            PostList(
                modifier = Modifier.weight(1f),
                uiState = uiState
            )
        }
    }
}