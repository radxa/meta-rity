# Copyright (C) 2022 MediaTek Inc.
#   Author: Ramax Lo <ramax.lo@mediatek.com>
# Released under the MIT license (see COPYING.MIT for the terms)

require recipes-bringup/images/rity-bringup-image.bb

DESCRIPTION = "Rity BSP Image"

IMAGE_FEATURES += "ssh-server-dropbear"

IMAGE_INSTALL += " \
	packagegroup-base-extended \
	packagegroup-core-base-utils \
	packagegroup-rity-audio-extended \
	packagegroup-rity-debug-extended \
	packagegroup-rity-devel-extended \
	packagegroup-rity-display-extended \
	packagegroup-rity-multimedia-extended \
	packagegroup-rity-net-extended \
	packagegroup-rity-optee-extended \
	packagegroup-rity-tools-extended \
	packagegroup-rity-zeroconf-extended \
	packagegroup-tools-bluetooth \
	gstreamer1.0-meta-base \
	gstreamer1.0-meta-audio \
	gstreamer1.0-meta-debug \
	gstreamer1.0-meta-video \
	gstreamer1.0-python \
	e2fsprogs-resize2fs \
	iproute2 \
	can-utils \
"

IMAGE_INSTALL:remove:i300b = " \
	packagegroup-display \
	packagegroup-multimedia \
"

IMAGE_INSTALL:append:i1200 = " \
	packagegroup-rity-mtk-video \
	${@bb.utils.contains("DISTRO_FEATURES", "nda-mtk", "packagegroup-rity-mtk-neuropilot", "", d)} \
	${@bb.utils.contains("DISTRO_FEATURES", "nda-mtk", "packagegroup-rity-mtk-camisp", "", d)} \
"

IMAGE_INSTALL:append:i350 = " \
	packagegroup-rity-mtk-video \
"

