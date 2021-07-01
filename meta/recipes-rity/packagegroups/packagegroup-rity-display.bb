# Copyright (C) 2020 Fabien Parent <fparent@baylibre.com>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Rity Display packages"

inherit packagegroup

PACKAGES = " \
	${PN} \
	${PN}-extended \
"

VULKAN_PKGS = " \
	vulkan-samples \
	vulkan-tools \
"

RDEPENDS_${PN} = " \
	dmidecode \
	evtest \
	libdrm-tests \
	wayland \
	weston \
	weston-examples \
	weston-init \
	read-edid \
	kmscube \
	${@bb.utils.contains("DISTRO_FEATURES", "vulkan", "${VULKAN_PKGS}", "", d)} \
"

RDEPENDS_${PN}_append_i300a= " \
	imgtec-pvr-tests \
"

RDEPENDS_${PN}-extended = " \
	${PN} \
	gtk+3-demo \
"
