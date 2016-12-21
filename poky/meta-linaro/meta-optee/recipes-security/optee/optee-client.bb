SUMMARY = "OPTEE Client"
HOMEPAGE = "https://github.com/OP-TEE/optee_client"

LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${S}/LICENSE;md5=a6d62e1b5fef18a1854bd538e3160d7c"

PV = "2.2.0+git${SRCPV}"

inherit pythonnative systemd

SRC_URI = "git://github.com/OP-TEE/optee_client.git \
           file://tee-supplicant.service"
S = "${WORKDIR}/git"

SRCREV = "658ae538f76a2624b7f9c40539a600d281d872b4"

SYSTEMD_SERVICE_${PN} = "tee-supplicant.service"

do_compile() {
    install -d ${D}${prefix}
    oe_runmake EXPORT_DIR=${D}${prefix}
}

do_install() {
    install -d ${D}${libdir}
    install -d ${D}${bindir}

    # fix up hardcoded /lib paths
    sed -i -e 's:EXPORT_DIR}/lib:EXPORT_DIR}${base_libdir}:g' ${S}/Makefile

    oe_runmake install EXPORT_DIR=${D}${prefix}

    ( cd ${D}${libdir}
      rm libteec.so libteec.so.1
      ln -s libteec.so.1.0 libteec.so.1
      ln -s libteec.so.1.0 libteec.so
    )

    sed -i -e s:/etc:${sysconfdir}:g \
           -e s:/usr/bin:${bindir}:g \
              ${WORKDIR}/tee-supplicant.service

    install -D -p -m0644 ${WORKDIR}/tee-supplicant.service ${D}${systemd_system_unitdir}/tee-supplicant.service
}

