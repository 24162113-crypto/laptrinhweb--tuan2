<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:include page="/views/decorators/admin-header.jsp" />

<div class="d-flex justify-content-between align-items-center mb-4 bg-white p-3 rounded-3 shadow-sm">
    <h5 class="fw-bold m-0 text-primary"><i class="bi bi-plus-circle-fill me-2"></i>Thêm Sản Phẩm Mới</h5>
    <a href="${pageContext.request.contextPath}/admin/products" class="btn btn-outline-dark btn-sm px-3 rounded-pill fw-semibold">
        <i class="bi bi-arrow-left me-1"></i> Quay lại
    </a>
</div>

<div class="card border-0 shadow-sm rounded-3">
    <div class="card-body p-4">
        <form action="${pageContext.request.contextPath}/admin/product/insert" method="post" enctype="multipart/form-data">
            <div class="row g-4">
                <div class="col-lg-8">
                    <div class="mb-3">
                        <label class="form-label fw-bold text-dark">Tên sản phẩm <span class="text-danger">*</span></label>
                        <input type="text" name="productName" class="form-control form-control-lg fs-6 rounded-3 text-dark" required placeholder="Nhập tên sản phẩm...">
                    </div>

                    <div class="row g-3 mb-3">
                        <div class="col-md-6">
                            <label class="form-label fw-bold text-dark">Giá bán (VNĐ) <span class="text-danger">*</span></label>
                            <div class="input-group">
                                <input type="number" step="0.01" name="price" class="form-control rounded-start-3 text-dark" required placeholder="0">
                                <span class="input-group-text bg-light text-dark fw-bold">đ</span>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label fw-bold text-dark">Số lượng kho <span class="text-danger">*</span></label>
                            <input type="number" name="quantity" class="form-control rounded-3 text-dark" value="1" required>
                        </div>
                    </div>

                    <div class="mb-3">
                        <label class="form-label fw-bold text-dark">Mô tả sản phẩm</label>
                        <textarea name="description" class="form-control rounded-3 text-dark" rows="5" placeholder="Nhập mô tả chi tiết sản phẩm..."></textarea>
                    </div>
                </div>

                <div class="col-lg-4 border-start-lg">
                    <div class="bg-light p-3 rounded-3 mb-3 border">
                        <label class="form-label fw-bold text-dark">Danh mục sản phẩm <span class="text-danger">*</span></label>
                        <select name="categoryId" class="form-select rounded-3 text-dark" required>
                            <option value="" class="text-dark">-- Chọn danh mục --</option>
                            <c:forEach items="${categories != null ? categories : listcate}" var="c">
                                <option value="${c.categoryid}" class="text-dark">${c.categoryname}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="bg-light p-3 rounded-3 mb-3 border">
                        <label class="form-label fw-bold text-dark">Trạng thái kinh doanh</label>
                        <select name="status" class="form-select rounded-3 text-dark">
                            <option value="1" class="text-dark">Đang bán</option>
                            <option value="0" class="text-dark">Tạm ngưng</option>
                        </select>
                    </div>

                    <div class="bg-light p-3 rounded-3 border">
                        <label class="form-label fw-bold text-dark mb-2">Hình ảnh sản phẩm</label>

                        <div class="mb-3">
                            <small class="text-dark fw-semibold d-block mb-1">Cách 1: Upload file từ máy</small>
                            <input type="file" name="imageFile" class="form-control form-control-sm rounded-3 text-dark" accept="image/*">
                        </div>

                        <div>
                            <small class="text-dark fw-semibold d-block mb-1">Cách 2: Nhập URL ảnh online</small>
                            <input type="text" name="images" class="form-control form-control-sm rounded-3 text-dark" placeholder="https://example.com/image.jpg">
                        </div>
                    </div>
                </div>
            </div>

            <hr class="my-4">
            <div class="text-end">
                <button type="reset" class="btn btn-outline-secondary px-4 me-2 rounded-pill fw-bold">Làm mới</button>
                <button type="submit" class="btn btn-success px-4 rounded-pill shadow-sm fw-bold">
                    <i class="bi bi-check-lg me-1"></i> Lưu Sản Phẩm
                </button>
            </div>
        </form>
    </div>
</div>

<jsp:include page="/views/decorators/admin-footer.jsp" />