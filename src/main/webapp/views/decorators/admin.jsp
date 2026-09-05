<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><sitemesh:write property="title"/></title>
    
    <!-- Bootstrap 5 CSS CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons CDN -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <!-- FontAwesome CDN -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f4f6f9;
            color: #212529 !important;
        }
        
        /* Sidebar Styling */
        .sidebar {
            width: 260px;
            min-height: 100vh;
            background: #1e293b;
            color: #f8fafc;
        }
        
        .sidebar .nav-link {
            color: #cbd5e1 !important;
            padding: 12px 20px;
            margin: 4px 12px;
            border-radius: 8px;
            font-weight: 500;
            transition: all 0.2s ease;
        }
        
        .sidebar .nav-link:hover, .sidebar .nav-link.active {
            background-color: #0d6efd;
            color: #ffffff !important;
        }
        
        /* Header Bar Styling */
        .admin-header {
            background-color: #ffffff;
            border-bottom: 1px solid #e2e8f0;
            padding: 12px 24px;
        }
        
        /* User Dropdown */
        .dropdown-menu {
            border: none;
            box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1);
        }
        
        .dropdown-item {
            color: #334155 !important;
            padding: 8px 16px;
        }
        
        .dropdown-item:hover {
            background-color: #f1f5f9;
            color: #0d6efd !important;
        }

        /* Override tất cả chữ trắng vô tình bị ẩn */
        .main-content * {
            color: inherit;
        }
    </style>
</head>
<body>

    <div class="d-flex">
        <!-- Sidebar -->
        <aside class="sidebar d-flex flex-column flex-shrink-0 shadow">
            <a href="${pageContext.request.contextPath}/admin/products" class="d-flex align-items-center mb-3 mb-md-0 me-md-auto text-white text-decoration-none p-3">
                <i class="bi bi-shield-lock-fill fs-3 text-primary me-2"></i>
                <span class="fs-5 fw-bold">Admin Panel</span>
            </a>
            <hr class="border-secondary my-2">
            
            <ul class="nav nav-pills flex-column mb-auto">
                <li class="nav-item">
                    <a href="${pageContext.request.contextPath}/admin/products" class="nav-link active">
                        <i class="bi bi-box-seam me-2"></i> Quản lý sản phẩm
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/admin/categories" class="nav-link">
                        <i class="bi bi-tags me-2"></i> Quản lý danh mục
                    </a>
                </li>
            </ul>
            <hr class="border-secondary my-2">
            <div class="p-3">
                <small class="text-secondary">© 2026 JPA Admin System</small>
            </div>
        </aside>

        <!-- Main Wrapper -->
        <div class="flex-grow-1 d-flex flex-column min-vh-100">
            <!-- Header Topbar -->
            <header class="admin-header d-flex justify-content-between align-items-center shadow-sm">
                <div class="fw-semibold text-dark">
                    <i class="bi bi-speedometer2 text-primary me-1"></i> Trang quản trị hệ thống
                </div>

                <!-- User Dropdown Menu -->
                <div class="dropdown">
                    <button class="btn btn-light border dropdown-toggle d-flex align-items-center gap-2 fw-semibold text-dark" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                        <i class="bi bi-person-circle fs-5 text-primary"></i> Quản trị viên
                    </button>
                    <ul class="dropdown-menu dropdown-menu-end rounded-3 mt-2">
                        <li>
                            <a class="dropdown-item fw-medium" href="#">
                                <i class="bi bi-person me-2 text-primary"></i> Hồ sơ cá nhân
                            </a>
                        </li>
                        <li><hr class="dropdown-divider"></li>
                        <li>
                            <a class="dropdown-item fw-medium text-danger" href="${pageContext.request.contextPath}/logout">
                                <i class="bi bi-box-arrow-right me-2"></i> Đăng xuất
                            </a>
                        </li>
                    </ul>
                </div>
            </header>

            <!-- Main Content Container -->
            <main class="main-content p-4 flex-grow-1">
                <sitemesh:write property="body" />
            </main>
        </div>
    </div>

    <!-- Bootstrap 5 JS Bundle (Có bao gồm PopperJS để bật Dropdown Menu) -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.min.js"></script>
</body>
</html>