<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Trang quản trị</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
    <style>
        .admin-sidebar { min-height: 100vh; background: #212529; }
        .admin-sidebar a { color: #adb5bd; display: block; padding: 10px 16px; text-decoration: none; }
        .admin-sidebar a:hover, .admin-sidebar a.active { color: #fff; background: #343a40; }
    </style>
</head>
<body>
    <div class="d-flex">
        <nav class="admin-sidebar" style="width: 220px;">
            <div class="text-white text-center py-3 border-bottom border-secondary">
                <strong>Admin Panel</strong>
            </div>
            <a href="${pageContext.request.contextPath}/admin/categories">Quản lý danh mục</a>
            <a href="${pageContext.request.contextPath}/admin/products">Quản lý sản phẩm</a>
            <hr class="border-secondary mx-3">
            <a href="${pageContext.request.contextPath}/home">Về trang chủ</a>
            <a href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
        </nav>
        <main class="flex-grow-1 p-4">
            <c:if test="${not empty sessionScope.account}">
                <div class="text-end text-muted mb-2">
                    Xin chào, <strong>${sessionScope.account.fullname}</strong>
                </div>
            </c:if>