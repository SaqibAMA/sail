package com.functrco.sail.receivers
import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.functrco.sail.utils.Util


class BluetoothBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent!!.action

        if (action == BluetoothAdapter.ACTION_STATE_CHANGED) {
            val state = intent!!.getIntExtra(
                BluetoothAdapter.EXTRA_STATE,
                BluetoothAdapter.ERROR
            )
            when (state) {
                BluetoothAdapter.STATE_OFF -> Util.showToast(context, "Bluetooth off")
                BluetoothAdapter.STATE_TURNING_OFF -> Util.showToast(context, "Turning Bluetooth off...")
                BluetoothAdapter.STATE_ON -> Util.showToast(context, "Bluetooth on")
                BluetoothAdapter.STATE_TURNING_ON -> Util.showToast(context, "Turning Bluetooth on...")
            }
        }

    }
}