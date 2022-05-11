package ru.daniilxt.feature.template

import ru.daniilxt.feature.template.Utils.toSnakeCase

class GenerateRecyclerAdapter(
    private val moduleName: String,
    private val adapterName: String,
    private val dataClass: Class<*>,
    private val itemLayout: String = "Item$adapterName"
) {
    private var packageName = "ru.daniilxt"
    private var directoryPath = "feature/src/main/java/ru/daniilxt/feature/"
    private var viewHolderPath = "presentation/adapter/view_holder"
    private var diffUtilPath = "presentation/adapter/diffutil"
    private var adapterPath = "presentation/adapter"
    private var directoryLayoutPath = "feature/src/main/res/layout/"

    private val dataClassName: String = dataClass.simpleName
    private val dataClassFields =
        dataClass.declaredFields.filter { it.name != "CREATOR" }.map { it.name }

    fun main() {
        Utils.createFile(
            "$directoryPath/$moduleName/$viewHolderPath",
            "${adapterName}ViewHolder.kt",
            generateViewHolder()
        )
        Utils.createFile(
            "$directoryPath/$moduleName/$diffUtilPath",
            "${adapterName}Callback.kt",
            generateDiffUtilCallback()
        )
        Utils.createFile(
            "$directoryPath/$moduleName/$adapterPath",
            "${adapterName}Adapter.kt",
            generateAdapter()
        )
        Utils.createFile(
            directoryLayoutPath,
            "${itemLayout.replaceFirstChar { it.lowercaseChar() }.toSnakeCase()}.xml",
            generateXmlItemView()
        )
    }

    private fun generateViewHolder() =
        """
        package $packageName.feature.$moduleName.presentation.adapter.view_holder

        import androidx.recyclerview.widget.RecyclerView
        import $packageName.feature.databinding.${itemLayout}Binding
        import $packageName.feature.domain.model.$dataClassName

        class ${adapterName}ViewHolder(
            private val binding: ${itemLayout}Binding
        ) : RecyclerView.ViewHolder(binding.root) {
            fun bind(data: $dataClassName) {
            }
        }
        """.trimIndent()

    private fun generateDiffUtilCallback() =
        """
            package $packageName.feature.$moduleName.presentation.adapter.diffutil

            import androidx.recyclerview.widget.DiffUtil
            import $packageName.feature.domain.model.$dataClassName

            class ${adapterName}Callback : DiffUtil.ItemCallback<$dataClassName>() {
                override fun areItemsTheSame(
                    oldItem: $dataClassName,
                    newItem: $dataClassName
                ): Boolean {
                    return oldItem.${dataClassFields.first()} == newItem.${dataClassFields.first()}
                }

                override fun areContentsTheSame(
                    oldItem: $dataClassName,
                    newItem: $dataClassName
                ): Boolean {
                    return ${generateClassCompFields(dataClassFields)}
                }
            }
        """.trimIndent()

    private fun generateAdapter() =
        """
            package $packageName.feature.$moduleName.presentation.adapter

            import android.view.ViewGroup
            import androidx.recyclerview.widget.AsyncListDiffer
            import androidx.recyclerview.widget.RecyclerView
            import ru.daniilxt.common.extensions.viewBinding
            import $packageName.feature.databinding.${itemLayout}Binding
            import $packageName.feature.domain.model.$dataClassName
            import $packageName.feature.$moduleName.presentation.adapter.diffutil.${adapterName}Callback
            import $packageName.feature.$moduleName.presentation.adapter.view_holder.${adapterName}ViewHolder

            class ${adapterName}Adapter(
                private val onItemClickListener: (data: $dataClassName) -> Unit,
            ) : RecyclerView.Adapter<${adapterName}ViewHolder>() {
                private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

                override fun onCreateViewHolder(
                    parent: ViewGroup,
                    viewType: Int
                ): ${adapterName}ViewHolder =
                    ${adapterName}ViewHolder(parent.viewBinding(${itemLayout}Binding::inflate))

                override fun onBindViewHolder(holder: ${adapterName}ViewHolder, position: Int) {
                    holder.bind(
                        differ.currentList[position],
                        this.onItemClickListener,
                    )
                }

                override fun getItemCount(): Int {
                    return differ.currentList.size
                }

                fun bind(data: List<$dataClassName>) {
                    differ.submitList(data)
                }

                companion object {
                    private val DIFF_CALLBACK = ${adapterName}Callback()
                }
            }

        """.trimIndent()

    private fun generateXmlItemView() =
        """
            <com.google.android.material.card.MaterialCardView xmlns:android="http://schemas.android.com/apk/res/android"
                xmlns:app="http://schemas.android.com/apk/res-auto"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginHorizontal="@dimen/dp_8"
                android:layout_marginBottom="@dimen/dp_10"
                android:background="@android:color/transparent"
                app:cardCornerRadius="@dimen/dp_10"
                app:layout_constraintTop_toTopOf="parent">

                <androidx.constraintlayout.widget.ConstraintLayout
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content">
                    
                </androidx.constraintlayout.widget.ConstraintLayout>
            </com.google.android.material.card.MaterialCardView>
        """.trimIndent()

    private fun generateClassCompFields(fields: List<String>): String {
        val firstField = fields[0]
        return "oldItem.$firstField == newItem.$firstField" + fields.subList(1, fields.size)
            .map { field ->
                " && oldItem.$field == newItem.$field"
            }.joinToString { it }
    }
}
