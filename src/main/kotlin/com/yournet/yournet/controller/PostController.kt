package com.yournet.yournet.controller

import com.yournet.yournet.model.payload.post.request.PostWriteRequestDto
import com.yournet.yournet.model.payload.post.response.PostResponseDto
import com.yournet.yournet.service.PostService
import org.pcap4j.core.PcapHandle
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import org.pcap4j.core.Pcaps
import org.pcap4j.packet.IpV4Packet
import org.pcap4j.packet.Packet
import org.pcap4j.packet.TcpPacket
import org.pcap4j.packet.UdpPacket
import org.pcap4j.core.PcapNetworkInterface.PromiscuousMode
import org.pcap4j.core.PcapNetworkInterface


import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses

@RestController
class PostController(
    private val postService: PostService
) {

    //게시글 작성 api
    @Operation(summary = "게시글 작성 API")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "게시글 작성 성공",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = PostResponseDto::class)
                )]
            ),
            ApiResponse(responseCode = "400", description = "잘못된 요청", content = [Content()]),
            ApiResponse(responseCode = "500", description = "서버 오류", content = [Content()])
        ]
    )
    @PostMapping("post")
    fun writePost(
        @Parameter(description = "게시글 작성 정보", required = true)
        @RequestBody body: PostWriteRequestDto,
        @RequestHeader("Authorization") jwt: String
    ): ResponseEntity<PostResponseDto> {
        return ResponseEntity.ok(postService.savePost(body, jwt))
    }

    // 특정 게시글 가져오기 API
    @Operation(summary = "특정 게시글 가져오기 API")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "게시글 조회 성공",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = PostResponseDto::class)
                )]
            ),
            ApiResponse(responseCode = "400", description = "잘못된 요청", content = [Content()]),
            ApiResponse(responseCode = "500", description = "서버 오류", content = [Content()])
        ]
    )
    @GetMapping("post/{postId}")
    fun getPost(
        @Parameter(description = "조회할 게시글 ID", required = true)
        @RequestParam("postId") postId: Int,
    ): ResponseEntity<PostResponseDto> {
        return ResponseEntity.ok(postService.getPost(postId))
    }


    // 게시글 리스트 조회 API
    @Operation(summary = "게시글 리스트 조회 API")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "게시글 리스트 조회 성공",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = PostResponseDto::class)
                )]
            ),
            ApiResponse(responseCode = "400", description = "잘못된 요청", content = [Content()]),
            ApiResponse(responseCode = "500", description = "서버 오류", content = [Content()])
        ]
    )
    @GetMapping("posts")
    fun getPosts(
        @Parameter(description = "페이지 번호", required = true)
        @RequestParam("page") page: Int,
        @Parameter(description = "페이지 크기", required = true)
        @RequestParam("size") size: Int,
        @Parameter(description = "정렬 기준", required = false, example = "createdAt")
        @RequestParam(required = false, defaultValue = "createdAt") sort: String?,
        @Parameter(description = "해시태그", required = false)
        @RequestParam(required = false) hashTag: String?,
    ): ResponseEntity<List<PostResponseDto>> {
        return ResponseEntity.ok(postService.getPostsList(page, size, sort, hashTag))
    }

    @PatchMapping("post/{postId}")
    @Operation(summary = "게시글 수정 API")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "게시글 수정 성공",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = PostResponseDto::class)
                )]
            ),
            ApiResponse(
                responseCode = "400",
                description = "잘못된 요청",
                content = [Content()]
            ),
            ApiResponse(
                responseCode = "401",
                description = "인증 실패",
                content = [Content()]
            ),
            ApiResponse(
                responseCode = "500",
                description = "서버 오류",
                content = [Content()]
            )
        ]
    )
    fun updatePost(
        @Parameter(
            description = "수정할 게시글 ID",
            required = true,
            example = "1"
        )
        @RequestParam("postId") postId: Int,
        @Parameter(
            description = "게시글 수정 정보",
            required = true,
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = PostWriteRequestDto::class)
            )]
        )
        @RequestBody body: PostWriteRequestDto,
        @RequestHeader("Authorization") jwt: String
    ): ResponseEntity<PostResponseDto> {
        return ResponseEntity.ok(postService.updatePost(postId, body, jwt))
    }

}