package com.naez.colivingapp.ui.spaces

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.naez.colivingapp.R
import com.naez.colivingapp.ui.common.basicDiffUtil
import com.naez.colivingapp.ui.common.inflate
import com.naez.colivingapp.ui.common.loadUrl
import com.naez.colivingapp.utils.Money
import com.naez.colivingapp.utils.currencyFormat
import com.naez.domain.Space
import kotlinx.android.synthetic.main.view_space.view.*

class SpaceAdapter(private val listener: (Space) -> Unit) :
    RecyclerView.Adapter<SpaceAdapter.ViewHolder>() {

    var spaces: List<Space> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.view_space, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = spaces.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val space = spaces[position]
        holder.bind(space)
        holder.itemView.setOnClickListener { listener(space) }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(space: Space) {
            itemView.descriptionSpace?.text = space.description
            itemView.amountSpace?.text = Money.format(space.amount, space.amountCurrency).currencyFormat()
            itemView.typeLivingPlace?.text = space.typeLivingPlace
            itemView.spaceCover?.loadUrl(space.image)
        }
    }
}