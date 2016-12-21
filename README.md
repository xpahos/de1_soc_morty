Yocto Linux 2.2(Morty) for Altera DE1-SoC(hard fpu)

Usage:
======

  sed 's/\/home\/xpahos\/yocto\/morty/<$PWD>/g' build/conf/bblayers.conf
  sed 's/\/home\/xpahos\/yocto\/morty/<$PWD>/g' build//conf/sanity_info
  source poky/oe-init-build-env build
  bitbake core-image-minimal
