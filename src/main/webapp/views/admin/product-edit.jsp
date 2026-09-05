<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:include page="/views/decorators/admin-header.jsp" />

<div class="d-flex justify-content-between align-items-center mb-4 bg-white p-3 rounded-3 shadow-sm">
    <h5 class="fw-bold m-0 text-primary"><i class="bi bi-pencil-square me-2"></i>Cập Nhật Sản Phẩm</h5>
    <a href="${pageContext.request.contextPath}/admin/products" class="btn btn-outline-dark btn-sm px-3 rounded-pill fw-semibold">
        <i class="bi bi-arrow-left me-1"></i> Quay lại
    </a>
</div>

<div class="card border-0 shadow-sm rounded-3">
    <div class="card-body p-4">
        <form action="${pageContext.request.contextPath}/admin/product/update" method="post" enctype="multipart/form-data">
            <input type="hidden" name="productId" value="${product.productid}">

            <div class="row g-4">
                <div class="col-lg-8">
                    <div class="mb-3">
                        <label class="form-label fw-bold text-dark">Tên sản phẩm <span class="text-danger">*</span></label>
                        <input type="text" name="productName" class="form-control form-control-lg fs-6 rounded-3 text-dark" value="${product.productname}" required>
                    </div>

                    <div class="row g-3 mb-3">
                        <div class="col-md-6">
                            <label class="form-label fw-bold text-dark">Giá bán (VNĐ) <span class="text-danger">*</span></label>
                            <div class="input-group">
                                <input type="number" step="0.01" name="price" class="form-control rounded-start-3 text-dark" value="${product.price}" required>
                                <span class="input-group-text bg-light text-dark fw-bold">đ</span>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label fw-bold text-dark">Số lượng kho <span class="text-danger">*</span></label>
                            <input type="number" name="quantity" class="form-control rounded-3 text-dark" value="${product.quantity}" required>
                        </div>
                    </div>

                    <div class="mb-3">
                        <label class="form-label fw-bold text-dark">Mô tả sản phẩm</label>
                        <textarea name="description" class="form-control rounded-3 text-dark" rows="5">${product.description}</textarea>
                    </div>
                </div>

                <div class="col-lg-4 border-start-lg">
                    <div class="bg-light p-3 rounded-3 mb-3 border">
                        <label class="form-label fw-bold text-dark">Danh mục sản phẩm <span class="text-danger">*</span></label>
                        <select name="categoryId" class="form-select rounded-3 text-dark" required>
                            <c:forEach items="${categories != null ? categories : listcate}" var="c">
                                <option value="${c.categoryid}" class="text-dark" ${c.categoryid == product.category.categoryid ? 'selected' : ''}>
                                    ${c.categoryname}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="bg-light p-3 rounded-3 mb-3 border">
                        <label class="form-label fw-bold text-dark">Trạng thái kinh doanh</label>
                        <select name="status" class="form-select rounded-3 text-dark">
                            <option value="1" class="text-dark" ${product.status == 1 ? 'selected' : ''}>Đang bán</option>
                            <option value="0" class="text-dark" ${product.status == 0 ? 'selected' : ''}>Tạm ngưng</option>
                        </select>
                    </div>

                    <div class="bg-light p-3 rounded-3 border">
                        <label class="form-label fw-bold text-dark mb-2">Ảnh Hiện Tại</label>
                        <div class="text-center mb-3 bg-white p-2 rounded-3 border">
                            <c:choose>
                                <c:when test="${product.images != null && product.images.startsWith('http')}">
                                    <img src="${product.images}" class="img-fluid rounded-2 object-fit-cover" style="max-height: 140px;">
                                </c:when>
                                <c:otherwise>
                                    <img src="${pageContext.request.contextPath}/image?fname=${product.images}" class="img-fluid rounded-2 object-fit-cover" style="max-height: 140px;">
                                </c:otherwise>
                            </c:choose>
                        </div>

                        <div class="mb-2">
                            <small class="text-dark fw-semibold d-block mb-1">Thay bằng Upload File mới</small>
                            <input type="file" name="imageFile" class="form-control form-control-sm rounded-3 text-dark" accept="image/*">
                        </div>

                        <div>
                            <small class="text-dark fw-semibold d-block mb-1">Hoặc thay bằng Link URL mới</small>
                            <input type="text" name="images" class="form-control form-control-sm rounded-3 text-dark" value="${product.images.startsWith('http') ? product.images : ''}">
                        </div>
                    </div>
                </div>
            </div>

            <hr class="my-4">
            <div class="text-end">
                <button type="submit" class="btn btn-primary px-4 rounded-pill shadow-sm fw-bold">
                    <i class="bi bi-save me-1"></i> Cập Nhật Sản Phẩm
                </button>
            </div>
        </form>
    </div>
</div>

<jsp:include page="/views/decorators/admin-footer.jsp" />