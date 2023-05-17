package com.yournet.yournet.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.BufferedReader
import java.io.InputStreamReader

@RestController
@RequestMapping("/packet")
class PacketCaptureController {

    @PostMapping
    fun capturePacket(@RequestBody request: Any): ResponseEntity<String> {
        val tcpdumpCommand = arrayOf("tcpdump", "-i", "eth0", "-c", "1", "-n", "-s", "0", "tcp")
        val process = Runtime.getRuntime().exec(tcpdumpCommand)

        val reader = BufferedReader(InputStreamReader(process.inputStream))
        val output = StringBuilder()

        var line: String?
        while (reader.readLine().also { line = it } != null) {
            output.append(line)
        }

        process.waitFor()

        // TCPDUMP 출력에서 원하는 정보 추출
        val packetData = extractPacketData(output.toString())
        println(output.toString())
        // 요청 정보와 클라이언트 IP 주소 출력
        val requestBody = request.toString()
        val clientIpAddress = packetData.clientIpAddress

        println("Request Body: $requestBody")
        println("Client IP Address: $clientIpAddress")

        return ResponseEntity.ok("Packet capture completed")
    }

    private fun extractPacketData(tcpdumpOutput: String): PacketData {
        // TCPDUMP 출력에서 필요한 정보를 추출하는 로직을 구현해야 합니다.
        // 예시로서 간단히 구조체를 사용하여 필요한 정보를 반환합니다.
        val packetData = PacketData()
        // 추출 로직 작성

        return packetData
    }
}

data class PacketData(
    val clientIpAddress: String = "",
    val serverIpAddress: String = "",
    val clientPort: Int = 0,
    val serverPort: Int = 0,
    val payload: String = ""
)
