package com.dream.live.cricket.score.hd.streaming.emulator

sealed class DeviceState {

    class Emulator(
        val source: VerdictSource
    ) : DeviceState()

    object NotEmulator : DeviceState()
}