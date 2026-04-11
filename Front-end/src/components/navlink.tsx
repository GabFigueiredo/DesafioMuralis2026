'use client'

import { cn } from "@/lib/utils";
import Link, { LinkProps } from "next/link";
import { usePathname } from "next/navigation";

interface NavLinkProps extends LinkProps {
    children: React.ReactNode;
} 

export function NavLink({ children, href, ...rest }: NavLinkProps ) {
    const linkPath = typeof href === 'string' ? href : href.pathname;
    const pathname = usePathname();
    const isActive = pathname === linkPath || pathname?.startsWith(`${linkPath}/`);

    return (
        <Link
            {...rest}
            href={href}
            className={cn(
                'flex items-center gap-2 text-white text-[1rem] font-medium no-underline py-2 px-4 rounded-md transition-all duration-200 hover:bg-OrangeMuralis',
                'transition-colors',
                isActive ? 'bg-OrangeMuralis' : ''
            )}
            >
            {children}
        </Link>
    )
}
